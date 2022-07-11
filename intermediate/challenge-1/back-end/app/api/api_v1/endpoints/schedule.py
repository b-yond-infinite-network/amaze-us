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
    return crud.schedule.create(db, obj_in=schedule_in)

