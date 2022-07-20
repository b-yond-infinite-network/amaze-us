from src.models.driver_model import Driver
from src.schemas.driver_schema import DriverCreate, DriverUpdate
from src.services.base_service import BaseService


class DriverService(BaseService[Driver, DriverCreate, DriverUpdate]):
    ...


driver = DriverService(Driver)
