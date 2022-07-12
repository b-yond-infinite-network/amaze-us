from typing import List, Optional

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.api import deps
from app import crud, schemas


router = APIRouter()


@router.get("/", response_model=List[schemas.Schedule])
def get_schedules(
        *,
        db: Session = Depends(deps.get_db)) -> List[Optional[schemas.Schedule]]:
    return crud.schedule.get_multi(db)


@router.post("/", response_model=schemas.Schedule)
def create_schedule(
        *,
        db: Session = Depends(deps.get_db),
        schedule_in: schemas.ScheduleCreate) -> schemas.Schedule:
    driver = crud.driver.get(db=db, id=schedule_in.driver_id)
    if not driver:
        raise HTTPException(status_code=404, detail="Driver not found")

    bus = crud.bus.get(db=db, id=schedule_in.bus_id)
    if not bus:
        raise HTTPException(status_code=404, detail="Bus not found")

    schedules_with_intersection = crud.schedule.get_schedules_with_intersection(db=db, schedule_in=schedule_in)
    if len(schedules_with_intersection):
        raise HTTPException(status_code=422, detail="The driver or bus schedule overlaps with the existing one")

    return crud.schedule.create(db, obj_in=schedule_in)

