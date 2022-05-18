from typing import Any, List

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

import crud
import models
import schemas
from api import deps

router = APIRouter()


@router.get("/{id}", response_model=schemas.Driver)
def get_driver(
        *,
        db: Session = Depends(deps.get_db),
        id: int,
        current_user: models.Driver = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Get item by ID.
    """
    driver = crud.driver.get_by_id(db=db, id=id)
    if not driver:
        raise HTTPException(status_code=404, detail="Driver not found")
    return driver


@router.get("/", response_model=List[schemas.Driver])
def read_drivers(
        db: Session = Depends(deps.get_db),
        current_user: models.Driver = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Retrieve drivers.
    """
    drivers = crud.driver.get_all(db)
    return drivers


@router.post("/", response_model=schemas.Driver)
def create_driver(
        *,
        db: Session = Depends(deps.get_db),
        driver_in: schemas.DriverCreate,
        current_user: models.Driver = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Create new driver.
    """
    driver = crud.driver.get_by_ssn(db=db, ssn=driver_in.ssn)
    if driver:
        raise HTTPException(
            status_code=400,
            detail="The driver with this SSN already exists in the system.",
        )
    driver = crud.driver.create(db, obj_in=driver_in)
    return driver


@router.put("/{id}", response_model=schemas.Driver)
def update_item(
        *,
        db: Session = Depends(deps.get_db),
        id: int,
        driver_in: schemas.DriverUpdate,
        current_user: models.User = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Update a Driver.
    """
    # if not crud.user.is_manager(current_user) and (item.owner_id != current_user.id):
    #     raise HTTPException(status_code=400, detail="Not enough permissions")
    driver = crud.driver.get_by_id(db=db, id=id)
    if not driver:
        raise HTTPException(status_code=404, detail="Driver not found")

    driver = crud.driver.update(db=db, db_obj=driver, obj_in=driver_in)
    return driver


@router.delete("/{id}", response_model=schemas.Driver)
def delete_driver(
        id,
        *,
        db: Session = Depends(deps.get_db),
        current_user: models.Driver = Depends(deps.get_current_active_manager),
) -> Any:
    """
    Delete driver.
    """
    bus = crud.driver.get_by_id(db=db, id=id)
    if not bus:
        raise HTTPException(status_code=404, detail="Driver not found")
    driver = crud.driver.remove(db, id=id)
    return driver
