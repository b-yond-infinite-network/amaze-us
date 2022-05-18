from datetime import date, timedelta
from typing import List, Union

from sqlalchemy import and_
from sqlalchemy.orm import Session

from crud.crud_base import CRUDBase
from models.schedule import Schedule
from schemas.schedule import ScheduleCreate, ScheduleUpdate, ScheduleGet


class CRUDSchedule(CRUDBase[Schedule, ScheduleCreate, ScheduleUpdate]):
    def get_schedules(
            self, driver_id: Union[int, None], bus_id: Union[int, None], date: Union[date, None], db: Session
    ) -> List[ScheduleGet]:
        attributes = [self.model.date, self.model.start, self.model.end, self.model.bus_id, self.model.driver_id]
        filters = list()
        if driver_id:
            filters.append(self.model.driver_id == driver_id)
            attributes.remove(self.model.driver_id)
        if bus_id:
            filters.append(self.model.bus_id == bus_id)
            attributes.remove(self.model.bus_id)
        if date:
            filters.append(and_(self.model.date >= date, self.model.date <= date + timedelta(days=7)))
        schedules = super().get_with_filter(db, filters=filters, attributes=attributes)
        return schedules


schedule = CRUDSchedule(Schedule)
