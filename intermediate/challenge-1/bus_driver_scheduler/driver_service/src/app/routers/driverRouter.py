from fastapi import APIRouter, Depends, Path
from sqlalchemy.orm import Session
from app.config.database import get_db_session
from app.schemas.driver import DriverSchema, DriverDb
from app.services.DriverService import DriverService

# The requests entrypoint. Is similar to a controller class.
router = APIRouter(
    prefix="/driver",
    tags=["Driver"]
)


@router.post("/", description="Endpoint to create a new driver resource.", response_model=DriverDb, status_code=201)
def create_driver(payload: DriverSchema, db: Session = Depends(get_db_session)):
    return DriverService(db_session=db).create_driver(payload)


@router.put("/{id}/", description="Endpoint to update a driver", response_model=DriverDb)
def update_driver(payload: DriverSchema, id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return DriverService(db_session=db).update_driver(payload=payload, id=id)


@router.get("/{id}/", description="Endpoint to retrieve a driver", response_model=DriverDb)
def get_driver(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return DriverService(db_session=db).get_driver(id)


@router.delete("/{id}/", description="Endpoint to delete a driver.")
def delete_driver(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    DriverService(db_session=db).delete_driver(id)
