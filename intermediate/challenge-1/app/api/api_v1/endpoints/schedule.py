from datetime import date
from typing import Any, Optional

from fastapi import APIRouter, Depends, HTTPException, BackgroundTasks
from sqlalchemy.orm import Session

import crud
import models
import schemas
from api import deps
from core.config import settings
from utils import send_shift_email

router = APIRouter()


@router.post("/", response_model=schemas.Schedule)
async def create_shift(
        *,
        db: Session = Depends(deps.get_db),
        schedule_in: schemas.ScheduleCreate,
        current_user: models.Schedule = Depends(deps.get_current_active_manager),
        background_tasks: BackgroundTasks
) -> Any:
    """
    Create new shift.
    """
    driver = crud.driver.get_by_id(db=db, id=schedule_in.driver_id)
    if not driver:
        raise HTTPException(status_code=404, detail="Driver not found")
    bus = crud.bus.get_by_id(db=db, id=schedule_in.bus_id)
    if not bus:
        raise HTTPException(status_code=404, detail="Bus not found")
    schedule = crud.schedule.create(db, obj_in=schedule_in)
    if settings.EMAILS_ENABLED:
        background_tasks.add_task(send_shift_email,
                                  email_to=driver.email,
                                  driver_name=driver.first_name + driver.last_name,
                                  date=schedule.date,
                                  start=schedule.start,
                                  end=schedule.end,
                                  bus_id=schedule.bus_id)
    return schedule


@router.get("/", response_model=schemas.ScheduleGet)
def get_schedules(
        driver_id: Optional[int] = None,
        bus_id: Optional[int] = None,
        date: Optional[date] = None,
        db: Session = Depends(deps.get_db),
        current_user: models.Driver = Depends(deps.get_current_active_user),
) -> Any:
    """
    Retrieve schedules.
    """
    if not driver_id and not bus_id and not date:
        schedules = crud.schedule.get_all(db)
        return schedules

    list_schedule = crud.schedule.get_schedules(driver_id, bus_id, date, db)
    return list_schedule
