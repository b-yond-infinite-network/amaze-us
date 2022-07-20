from typing import List

from fastapi import APIRouter, status
from sqlalchemy.orm import Session

from src.context.user_context import CurrentUser
from src.database.db import ActiveSession
from src.exceptions.app_exception import AppException
from src.exceptions.auth_exception import AuthException
from src.models.user_model import User
from src.schemas.bus_schema import Bus, BusCreate, BusUpdate
from src.services.bus_service import bus as bus_service

router = APIRouter(prefix="/buses", tags=["Bus"])


@router.get("/")
async def get_all(
    db: Session = ActiveSession, skip: int = 0, limit: int = 100
) -> List[Bus]:
    """
    Return a list of bus
    """
    buses = bus_service.get_all(db_session=db, skip=skip, limit=limit)
    if not buses or buses is None:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")
    return buses


@router.get("/{bus_id}")
async def get_by_id(bus_id: int, db: Session = ActiveSession) -> Bus:
    """
    Return a bus given an id
    """
    bus = bus_service.get(db_session=db, id=bus_id)
    if not bus:
        raise AppException(
            status_code=status.HTTP_204_NO_CONTENT,
            message="",
        )

    return bus


@router.post("/", status_code=status.HTTP_201_CREATED, response_model=Bus)
async def create(
    *,
    bus_in: BusCreate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser
) -> dict:
    """
    Create a new bus
    """
    if current_user.is_superuser is False:
        raise AuthException()

    bus = bus_service.create(db_session=db, input_object=bus_in)
    return bus


@router.put("/{bus_id}", response_model=Bus)
async def update(
    bus_id: int,
    *,
    bus_in: BusUpdate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser
) -> dict:
    """
    Update a bus given an id and data
    """
    if current_user.is_superuser is False:
        raise AuthException()

    original_bus = bus_service.get(db_session=db, id=bus_id)
    updated_bus = bus_service.update(
        db_session=db, db_obj=original_bus, input_object=bus_in
    )
    return updated_bus


@router.delete("/{bus_id}")
async def remove(
    bus_id: int, db: Session = ActiveSession, current_user: User = CurrentUser
):
    """
    Remove a bus given an id
    """
    if current_user.is_superuser is False:
        raise AuthException

    bus = bus_service.remove(db_session=db, id=bus_id)
    return bus
