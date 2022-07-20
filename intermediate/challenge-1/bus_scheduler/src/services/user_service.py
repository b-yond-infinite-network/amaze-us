from typing import Any, Dict, Optional, Union

from sqlalchemy.orm import Session

from src.schemas.user_schema import UserCreate, UserUpdate

from ..core.security import get_password_hash
from ..models import User
from .base_service import BaseService


class UserService(BaseService[User, UserCreate, UserUpdate]):
    def get_by_email(
        self, db_session: Session, *, email: str
    ) -> Optional[User]:
        return db_session.query(User).filter(User.email == email).first()

    def create(self, db_session: Session, *, input_object: UserCreate) -> User:
        create_data = input_object.dict()
        create_data.pop("password")
        db_obj = User(**create_data)
        db_obj.hashed_password = get_password_hash(input_object.password)

        return super(UserService, self)._add_and_save_data_on_database(
            db_session=db_session, db_obj=db_obj
        )

    def update(
        self,
        db_session: Session,
        *,
        db_obj: User,
        input_object: Union[UserUpdate, Dict[str, Any]]
    ) -> User:
        if isinstance(input_object, dict):
            update_data = input_object
        else:
            update_data = input_object.dict(exclude_unset=True)

        if "password" in update_data:
            update_data["hashed_password"] = get_password_hash(
                input_object.password
            )
            update_data.pop("password")

        return super(UserService, self).update(
            db_session, db_obj=db_obj, input_object=update_data
        )

    def is_superuser(self, user: User) -> bool:
        return user.is_superuser


user = UserService(User)
