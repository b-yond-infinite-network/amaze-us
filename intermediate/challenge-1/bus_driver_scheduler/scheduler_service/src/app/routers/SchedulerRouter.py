from datetime import date
from typing import List

from fastapi import APIRouter, Depends, Path
from sqlalchemy.orm import Session
from app.config.database import get_db_session
from app.schemas.BusSchedule import BusSchedule
from app.schemas.DriverSchedule import DriverSchedule
from app.schemas.Schedule import ScheduleSchema, ScheduleDb
from app.services.ScheduleService import ScheduleService

# The requests entrypoint. Similar to a controller.

router = APIRouter(
    prefix="/schedule",
    tags=["Scheduler"]
)


@router.post("/", description="Endpoint to create a new schedule resource.", response_model=ScheduleDb, status_code=201)
def create_schedule(payload: ScheduleSchema, db: Session = Depends(get_db_session)):
    return ScheduleService(db_session=db).create_schedule(payload)


@router.put("/{id}/", description="Endpoint to update a schedule", response_model=ScheduleDb)
def update_schedule(payload: ScheduleSchema, id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return ScheduleService(db_session=db).update_schedule(payload=payload, id=id)


@router.get("/{id}/", description="Endpoint to retrieve a schedule", response_model=ScheduleDb)
def get_schedule(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return ScheduleService(db_session=db).get_schedule(id)


@router.get("/bus", description="Endpoint to get week schedules for buses.", response_model=List[BusSchedule])
def get_bus_week_schedules(dt: date, db: Session = Depends(get_db_session)):
    return ScheduleService(db_session=db).get_bus_week_schedules(dt)


@router.get("/driver", description="Endpoint to get week schedules for drivers.", response_model=List[DriverSchedule])
def get_driver_week_schedules(dt: date, db: Session = Depends(get_db_session)):
    return ScheduleService(db_session=db).get_driver_week_schedules(dt)


@router.delete("/{id}/", description="Endpoint to delete a schedule.")
def delete_schedule(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    ScheduleService(db_session=db).delete_schedule(id)
