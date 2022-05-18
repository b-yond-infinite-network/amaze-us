from typing import Any, Optional
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
import crud
import models
import schemas
from api import deps

router = APIRouter()


@router.post("/", response_model=schemas.Bus)
def create_bus(
        *,
        db: Session = Depends(deps.get_db),
        bus_in: schemas.BusCreate,
        current_user: models.Bus = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Create new bus.
    """
    bus = crud.bus.create(db, obj_in=bus_in)
    return bus


@router.get("/")
def get_bus(
        *,
        db: Session = Depends(deps.get_db),
        current_user: models.Bus = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Get list of bus.
    """
    list_bus = crud.bus.get_all(db)

    return list_bus

@router.get("/{id}", response_model=schemas.Bus)
def get_bus(
        id,
        *,
        db: Session = Depends(deps.get_db),
        current_user: models.Bus = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Get bus by ID.
    """
    bus = crud.bus.get_by_id(db, id=id)
    return bus



@router.put("/{id}", response_model=schemas.Bus)
def update_bus(
        *,
        db: Session = Depends(deps.get_db),
        id: int,
        bus_in: schemas.BusUpdate,
        current_user: models.User = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Update a Bus.
    """
    bus = crud.bus.get_by_id(db=db, id=id)
    if not bus:
        raise HTTPException(status_code=404, detail="Bus not found")

    bus = crud.bus.update(db=db, db_obj=bus, obj_in=bus_in)
    return bus


@router.delete("/{id}", response_model=schemas.Bus)
def delete_bus(
        id,
        *,
        db: Session = Depends(deps.get_db),
        current_user: models.Bus = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Delete bus.
    """
    bus = crud.bus.get_by_id(db=db, id=id)
    if not bus:
        raise HTTPException(status_code=404, detail="Bus not found")
    bus = crud.bus.remove(db, id=id)
    return bus
