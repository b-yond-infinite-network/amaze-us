from app.crud.base import CRUDBase
from app.models.driver import Driver
from app.schemas.driver import DriverCreate, DriverUpdate


class CRUDDriver(CRUDBase[Driver, DriverCreate, DriverUpdate]):
    pass


driver = CRUDDriver(Driver)
