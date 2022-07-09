from typing import List, Optional

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.api import deps
from app import crud, schemas


router = APIRouter()


@router.get("/", response_model=List[schemas.Driver])
def get_drivers(
        *,
        db: Session = Depends(deps.get_db)) -> List[Optional[schemas.Driver]]:
    return crud.driver.get_multi(db)


@router.post("/", response_model=schemas.Driver)
def create_driver(
        *,
        db: Session = Depends(deps.get_db),
        driver_in: schemas.DriverCreate) -> schemas.Driver:
    return crud.driver.create(db, obj_in=driver_in)

