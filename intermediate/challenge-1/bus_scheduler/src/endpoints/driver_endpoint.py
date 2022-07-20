from typing import List

from fastapi import APIRouter, status
from sqlalchemy.orm import Session

from src.context.user_context import CurrentUser
from src.database.db import ActiveSession
from src.exceptions.app_exception import AppException
from src.exceptions.auth_exception import AuthException
from src.models.user_model import User
from src.schemas.driver_schema import Driver, DriverCreate, DriverUpdate
from src.services.driver_service import driver as driver_service

router = APIRouter(prefix="/drivers", tags=["Driver"])


@router.get("/")
async def get_all(
    db: Session = ActiveSession, skip: int = 0, limit: int = 100
) -> List[Driver]:
    """
    Return a list of driver
    """
    drivers = driver_service.get_all(db_session=db, skip=skip, limit=limit)
    if not drivers:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")
    return drivers


@router.get("/{driver_id}")
async def get_by_id(driver_id: int, db: Session = ActiveSession) -> Driver:
    """
    Return a driver given an id
    """
    driver = driver_service.get(db_session=db, id=driver_id)
    if driver is None:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return driver


@router.post("/", status_code=status.HTTP_201_CREATED, response_model=Driver)
async def create(
    *,
    driver_in: DriverCreate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser
) -> Driver:
    """
    Create a new driver
    """
    if current_user.is_superuser is False:
        raise AuthException()

    new_driver = driver_service.create(db_session=db, input_object=driver_in)
    return new_driver


@router.put("/{driver_id}", response_model=Driver)
async def update(
    driver_id: int,
    *,
    driver_in: DriverUpdate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser
) -> Driver:
    """
    Update a driver given an id and data
    """
    if current_user.is_superuser is False:
        raise AuthException()

    original_driver = driver_service.get(db_session=db, id=driver_id)
    updated_driver = driver_service.update(
        db_session=db, db_obj=original_driver, input_object=driver_in
    )
    return updated_driver


@router.delete("/{driver_id}")
async def remove(
    driver_id: int,
    db: Session = ActiveSession,
    current_user: User = CurrentUser,
) -> Driver:
    """
    Remove a driver given an id
    """
    if current_user.is_superuser is False:
        raise AuthException()

    return driver_service.remove(db_session=db, id=driver_id)
