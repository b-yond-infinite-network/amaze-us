from typing import Any, Dict, Generic, List, Optional, Type, TypeVar, Union

from fastapi import status
from fastapi.encoders import jsonable_encoder
from pydantic import BaseModel
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

from src.database.db import Base
from src.exceptions.app_exception import AppException

ModelType = TypeVar("ModelType", bound=Base)
CreateSchemaType = TypeVar("CreateSchemaType", bound=BaseModel)
UpdateSchemaType = TypeVar("UpdateSchemaType", bound=BaseModel)


class BaseService(Generic[ModelType, CreateSchemaType, UpdateSchemaType]):
    def __init__(self, model: Type[ModelType]):
        self.model = model

    def get(self, db_session: Session, id: Any) -> Optional[ModelType]:
        driver = (
            db_session.query(self.model).filter(self.model.id == id).first()
        )
        return driver

    def get_all(
        self, db_session: Session, *, skip: int = 0, limit: int = 100
    ) -> List[ModelType]:
        return db_session.query(self.model).offset(skip).limit(limit).all()

    def create(
        self, db_session: Session, *, input_object: CreateSchemaType
    ) -> ModelType:
        obj_in_data = jsonable_encoder(input_object)
        db_obj = self.model(**obj_in_data)
        return self._add_and_save_data_on_database(
            db_session=db_session, db_obj=db_obj
        )

    def update(
        self,
        db_session: Session,
        *,
        db_obj: ModelType,
        input_object: Union[UpdateSchemaType, Dict[str, Any]],
    ) -> ModelType:
        if db_obj is None:
            message = f"The requested {self.model.__name__} updatable object does not exists"
            raise AppException(
                message=message,
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                error=True,
                details="The given id probably does not exist in the database",
            )

        obj_data = jsonable_encoder(db_obj)
        if isinstance(input_object, dict):
            update_data = input_object
        else:
            update_data = input_object.dict(exclude_unset=True)
        for field in obj_data:
            if field in update_data:
                setattr(db_obj, field, update_data[field])
        return self._add_and_save_data_on_database(
            db_session=db_session, db_obj=db_obj
        )

    def remove(self, db_session: Session, *, id: int) -> ModelType:
        obj = db_session.query(self.model).get(id)
        if obj is None:
            message = f"The requested {self.model.__name__} object does not exists with this id"
            details = f"The informed object with id {id} probably does not exist in the database"
            raise AppException(message=message, error=True, details=details)

        db_session.delete(obj)
        db_session.commit()
        return obj

    def _add_and_save_data_on_database(
        self, db_session: Session, db_obj: ModelType
    ) -> ModelType:
        try:
            db_session.add(db_obj)
            db_session.commit()
            db_session.refresh(db_obj)
            return db_obj
        except SQLAlchemyError as e:
            db_session.rollback()
            raise e
        finally:
            db_session.close()
