from fastapi import APIRouter, Depends, Path
from sqlalchemy.orm import Session
from starlette.responses import Response

from app.config.database import get_db_session
from app.schemas.bus import BusSchema, BusDb
from app.services.BusService import BusService

# Requests entrypoint. Similar to a controller class.
router = APIRouter(
    prefix="/bus",
    tags=["Bus"]
)


@router.post("/", description="Endpoint to create a new bus resource.", response_model=BusDb, status_code=201)
def create_bus(payload: BusSchema, db: Session = Depends(get_db_session)):
    return BusService(db_session=db).create_bus(payload)


@router.put("/{id}/", response_model=BusDb)
def update_bus(payload: BusSchema, id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return BusService(db_session=db).update_bus(payload=payload, id=id)


@router.get("/{id}/", description="Endpoint to retrieve a bus", response_model=BusDb)
def get_bus(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    return BusService(db_session=db).get_bus(id)


@router.delete("/{id}/", description="Endpoint to delete a bus.")
def delete_bus(id: int = Path(gt=0), db: Session = Depends(get_db_session)):
    BusService(db_session=db).delete_bus(id)
