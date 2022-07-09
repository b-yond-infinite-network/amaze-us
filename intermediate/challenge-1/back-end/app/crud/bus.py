from app.crud.base import CRUDBase
from app.models.bus import Bus
from app.schemas.bus import BusCreate, BusUpdate


class CRUDBus(CRUDBase[Bus, BusCreate, BusUpdate]):
    pass


bus = CRUDBus(Bus)
