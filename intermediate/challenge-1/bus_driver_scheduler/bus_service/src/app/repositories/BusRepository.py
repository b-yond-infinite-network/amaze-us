from sqlalchemy.orm import Session

from app.models.bus import BusModel
from app.schemas.bus import BusSchema, BusDb


class BusRepository:
    #  Database layer connect all db operation are done here.
    def __init__(self, session: Session):
        self.db = session

    def post(self, payload: BusSchema) -> BusDb:
        """
        Create a new bus.
        :param payload: the bus data to be created.
        :return: The created bus.
        """
        bus_model = BusModel(**payload.dict())
        self.db.add(bus_model)
        self.db.commit()
        self.db.refresh(bus_model)
        return bus_model

    def get(self, id: int) -> BusDb:
        """
        Retrieve a bus by id.
        :param id: The bus id to search.
        :return: The bus.
        """
        return self.db.query(BusModel).filter(BusModel.id == id).first()

    def update(self, bus_model: BusModel, payload: BusSchema) -> BusDb:
        """
        Update a single bus.
        :param bus_model: The bus retrieved from db.
        :param payload: The new data to update the bus.
        :return: The newly updatged bus.
        """
        bus_model.model = payload.model
        bus_model.capacity = payload.capacity
        bus_model.driver_id = payload.driver_id
        bus_model.maker = payload.maker
        self.db.commit()
        return bus_model

    def delete(self, bus_model: BusModel) -> None:
        """
        Delete a bus.
        :param bus_model: The given bus to be deleted.
        :return:
        """
        self.db.delete(bus_model)
        self.db.commit()
