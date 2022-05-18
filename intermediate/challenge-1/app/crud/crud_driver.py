from typing import Optional

from sqlalchemy.orm import Session

from crud.crud_base import CRUDBase
from models.driver import Driver
from schemas.driver import DriverCreate, DriverUpdate


class CRUDDriver(CRUDBase[Driver, DriverCreate, DriverUpdate]):
    def get_by_ssn(self, db: Session, ssn: str) -> Optional[Driver]:
        return db.query(Driver).filter(Driver.ssn == ssn).first()

    def get_driver_email(self, db: Session, *, driver_id: str) -> str:
        return db.query(Driver.email).filter(Driver.driver_id == driver_id).first()


driver = CRUDDriver(Driver)
