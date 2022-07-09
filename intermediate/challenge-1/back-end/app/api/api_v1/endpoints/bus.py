from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.api import deps
from app import crud, schemas


router = APIRouter()


@router.get("/", response_model=list[schemas.Bus])
def get_buses(db: Session = Depends(deps.get_db)):
    buses = crud.bus.get_multi(db)
    return buses


@router.post("/", response_model=schemas.Bus)
def create_bus(
        *,
        db: Session = Depends(deps.get_db),
        bus_in: schemas.BusCreate) -> schemas.Bus:
    return crud.bus.create(db, obj_in=bus_in)
