from crud.crud_base import CRUDBase
from models.bus import Bus
from schemas.bus import BusGet, BusCreate, BusUpdate

class CRUDBus(CRUDBase[Bus, BusCreate, BusUpdate]):
    pass

bus = CRUDBus(Bus)
