from datetime import date
from typing import List

from fastapi import APIRouter, status, BackgroundTasks
from sqlalchemy.orm import Session

from src.core.email import send_new_schedule_email, send_update_schedule_email
from src.context.user_context import CurrentUser
from src.database.db import ActiveSession
from src.exceptions.app_exception import AppException
from src.exceptions.auth_exception import AuthException
from src.models.user_model import User
from src.schemas.schedule_schema import (
    Schedule,
    ScheduleCreate,
    ScheduleUpdate,
)
from src.services.base_service import ModelType
from src.services.schedule_service import schedule as schedule_service

router = APIRouter(prefix="/schedules", tags=["Schedule"])


@router.get(
    "/",
    responses={
        status.HTTP_200_OK: {"model": Schedule},
        status.HTTP_204_NO_CONTENT: {
            "model": None,
            "description": "There are no schedules in the database",
        },
    },
)
async def get_all_schedules(
    db: Session = ActiveSession, skip: int = 0, limit: int = 100
) -> List[ModelType]:
    """
    Returns a list of schedule
    """
    schedules = schedule_service.get_all(db_session=db, skip=skip, limit=limit)
    if not schedules:
        raise AppException(message="", status_code=status.HTTP_204_NO_CONTENT)
    return schedules


@router.get(
    "/{schedule_id}",
    responses={
        status.HTTP_200_OK: {"model": Schedule},
        status.HTTP_204_NO_CONTENT: {
            "model": None,
            "description": "The requested schedule was not found in the database",
        },
    },
)
async def get_schedule_by_id(
    schedule_id: int, db: Session = ActiveSession
) -> ModelType:
    """
    Returns a schedule given an id
    """
    schedule = schedule_service.get(db_session=db, id=schedule_id)
    if not schedule:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return schedule


@router.get("/drivers/{driver_id}/day/{day}")
async def get_driver_schedule_by_day(
    driver_id: int, day: date, db: Session = ActiveSession
) -> ModelType:
    """
    Returns a day driver schedule given a driver id and day
    """
    schedule = schedule_service.get_all_driver_day_schedules(
        db_session=db, driver_id=driver_id, day=day
    )
    if not schedule:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return schedule


@router.get("/buses/{bus_id}/day/{day}")
async def get_bus_schedule_by_day(
    bus_id: int, day: date, db: Session = ActiveSession
) -> ModelType:
    """
    Returns a day bus schedule given a bus id and day
    """
    schedule = schedule_service.get_all_bus_day_schedules(
        db_session=db, bus_id=bus_id, day=day
    )
    if not schedule:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return schedule


@router.get("/drivers/{driver_id}/week")
async def get_driver_schedule_by_week(
    driver_id: int, from_date: date, to_date: date, db: Session = ActiveSession
) -> ModelType:
    """
    Returns a weekly driver schedule given a driver id and date interval
    """
    schedule = schedule_service.get_all_driver_week_schedules(
        db_session=db,
        driver_id=driver_id,
        from_date=from_date,
        to_date=to_date,
    )
    if not schedule:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return schedule


@router.get("/buses/{bus_id}/week")
async def get_bus_schedule_by_week(
    bus_id: int, from_date: date, to_date: date, db: Session = ActiveSession
) -> ModelType:
    """
    Returns a week bus schedule given a bus id and date interval
    """
    schedule = schedule_service.get_all_bus_week_schedules(
        db_session=db, bus_id=bus_id, from_date=from_date, to_date=to_date
    )
    if not schedule:
        raise AppException(status_code=status.HTTP_204_NO_CONTENT, message="")

    return schedule


@router.post("/", status_code=status.HTTP_201_CREATED, response_model=Schedule)
async def create(
    *,
    schedule_in: ScheduleCreate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser,
    background_task: BackgroundTasks,
) -> ModelType:
    """
    Create a new schedule
    """
    if current_user.is_superuser is False:
        raise AuthException()

    schedule = schedule_service.create(db_session=db, input_object=schedule_in)
    background_task.add_task(
        send_new_schedule_email, schedule=schedule
    )

    return schedule


@router.put("/{schedule_id}", response_model=Schedule)
async def update(
    schedule_id: int,
    *,
    schedule_in: ScheduleUpdate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser,
    background_task: BackgroundTasks,
) -> ModelType:
    """
    Update a schedule given an id and data
    """
    if current_user.is_superuser is False:
        raise AuthException()

    original_schedule = schedule_service.get(db_session=db, id=schedule_id)
    original_data = None
    if original_schedule:
        original_data = {
            "original_driver_id": original_schedule.driver_id,
            "original_day": original_schedule.day,
            "original_start_hour": original_schedule.start_hour,
            "original_end_hour": original_schedule.end_hour
        }

    updated_schedule = schedule_service.update(db_session=db, db_obj=original_schedule, input_object=schedule_in)
    background_task.add_task(
        send_update_schedule_email, original_data, updated_schedule
    )

    return updated_schedule


@router.delete("/{schedule_id}")
async def remove(
    schedule_id: int,
    db: Session = ActiveSession,
    current_user: User = CurrentUser,
):
    """
    Remove a schedule given an id
    """
    if current_user.is_superuser is False:
        raise AuthException()

    schedule = schedule_service.remove(db_session=db, id=schedule_id)
    return schedule
