import logging

from fastapi import HTTPException
from sqlalchemy.orm import Session
from app.repositories.BusRepository import BusRepository
from app.schemas.bus import BusDb, BusSchema

logger = logging.getLogger(__name__)

# The service layer is used to process the data comming from the router/controller.
class BusService:
    def __init__(self, db_session: Session):
        self.bus_repo = BusRepository(session=db_session)

    def create_bus(self, payload: BusSchema) -> BusDb:
        """
        Receives a bus payload from controller to be persisted.
        :param payload: Bus data to persist.
        :return: A newly created bus.
        """
        return self.bus_repo.post(payload)

    def get_bus(self, id: int) -> BusDb:
        """
        Retrieve a bus from the db if exists, if not will raise error 404 bus not found.
        :param id: The bus id to search.
        :return: The searched bus.
        """
        bus: BusDb = self.bus_repo.get(id)
        if not bus:
            raise HTTPException(status_code=404, detail="Bus not found.")
        return bus

    def update_bus(self, payload: BusSchema, id: int) -> BusDb:
        """
        Update a bus.
        :param payload: The new data to update a single bus.
        :param id: The bus id to be updated.
        :return: The updated bus.
        """
        bus = self.get_bus(id)
        return self.bus_repo.update(bus_model=bus, payload=payload)

    def delete_bus(self, id: int) -> None:
        """
        Receives an id and search for the bus. If found the bus will be deleted.
        :param id: The bus id to be deleted.
        :return: None
        """
        bus = self.get_bus(id)
        self.bus_repo.delete(bus_model=bus)
