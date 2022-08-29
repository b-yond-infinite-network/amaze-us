from sqlalchemy.orm import Session

from app.models.driver import DriverModel
from app.schemas.driver import DriverSchema, DriverDb


class DriverRepository:
    #  Database layer connect all db operation are done here.
    def __init__(self, session: Session):
        self.db = session

    def post(self, payload: DriverSchema) -> DriverDb:
        """
        Create a new driver resource in the db.
        :param payload: The data used to create the new driver.
        :return: The new driver.
        """
        driver_model = DriverModel(**payload.dict())
        self.db.add(driver_model)
        self.db.commit()
        self.db.refresh(driver_model)
        return driver_model

    def get(self, id: int):
        """
        Retrieve a driver is exists. Otherwise return None
        :param id: The driver id to search for.
        :return: The driver.
        """
        return self.db.query(DriverModel).filter(DriverModel.id == id).first()

    def update(self, driver_model: DriverModel, payload: DriverSchema) -> DriverDb:
        """
        Update a single driver.
        :param driver_model: The driver to be upadted.
        :param payload: The data to update the new driver.
        :return: The newly updated driver.
        """
        driver_model.first_name = payload.first_name
        driver_model.last_name = payload.last_name
        driver_model.social_security_number = payload.social_security_number
        driver_model.email = payload.email
        self.db.commit()
        return driver_model

    def delete(self, driver_model: DriverModel) -> None:
        """
        Delete a single driver.
        :param driver_model: The driver instance to be deleted.
        :return: none
        """
        self.db.delete(driver_model)
        self.db.commit()
