from src.models.bus_model import Bus
from src.schemas.bus_schema import BusCreate, BusUpdate
from src.services.base_service import BaseService


class BusService(BaseService[Bus, BusCreate, BusUpdate]):
    ...


bus = BusService(Bus)
