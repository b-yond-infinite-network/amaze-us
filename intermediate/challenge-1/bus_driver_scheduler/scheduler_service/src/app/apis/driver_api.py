import logging

import requests

from app.config import settings
from fastapi import HTTPException

# Api class to connect to the bus service.
logger = logging.getLogger(__name__)


class DriverAPI:
    def __init__(self):
        self.url = settings.DRIVER_SERVICE_URL

    def get_driver_by_id(self, id: int) -> dict:
        """
        Get a driver by id.
        :param id: The driver id to search.
        :return: The driver.
        """
        try:
            response = requests.get(f"{self.url}/{id}")
            if response.status_code == 200:
                return response.json()

            if response.status_code == 404:
                raise HTTPException(status_code=response.status_code, detail="Driver not found. Create driver first.")

        except requests.exceptions.HTTPError as ehttp:
            logger.error(f"HTTP ERROR: {ehttp}")
        except requests.exceptions.Timeout as etimeout:
            logger.error(f"Timeout ERROR: {etimeout}")
        except requests.exceptions.RequestException as e:
            logger.error(f"Failed to retrieve driver: {e}")
