import logging

from fastapi import HTTPException
from sqlalchemy.orm import Session
from app.repositories.DriverRepository import DriverRepository
from app.schemas.driver import DriverDb, DriverSchema

# Service layer. It's used for processing data from the router and interact with the repository.


class DriverService:
    def __init__(self, db_session: Session):
        self.driver_repo = DriverRepository(session=db_session)

    def create_driver(self, payload: DriverSchema) -> DriverDb:
        """
        Creates a driver given a payload.
        :param payload: The driver data to create a new driver.
        :return: The created driver.
        """
        return self.driver_repo.post(payload)

    def get_driver(self, id: int) -> DriverDb:
        """
        Retrive a driver given an id. Search for the driver if found return the driver, otherwise raise 404 not found.
        :param id: The driver id to search for.
        :return: The driver.
        """
        driver: DriverDb = self.driver_repo.get(id)
        if not driver:
            raise HTTPException(status_code=404, detail="Driver not found.")
        return driver

    def update_driver(self, payload: DriverSchema, id: int) -> DriverDb:
        """
        Update a driver.
        :param payload: The data to be updated.
        :param id: The driver id the search for the driver to update the data.
        :return: The updated driver.
        """
        driver = self.get_driver(id)
        return self.driver_repo.update(driver_model=driver, payload=payload)

    def delete_driver(self, id: int) -> None:
        """
        Delete a driver. Search the driver by id.
        :param id: The driver id to find the driver.
        :return:
        """
        driver = self.get_driver(id)
        self.driver_repo.delete(driver_model=driver)
