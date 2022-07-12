from typing import List

from sqlalchemy.orm import Session

from app.crud.base import CRUDBase
from app.models.schedule import Schedule
from app.schemas.schedule import ScheduleGet, ScheduleCreate, ScheduleUpdate


class CRUDSchedule(CRUDBase[Schedule, ScheduleCreate, ScheduleUpdate]):
    def get_schedules_with_intersection(
            self, schedule_in: ScheduleCreate, db: Session
    ) -> List[ScheduleGet]:
        return db.query(Schedule).filter(
            (Schedule.bus_id == schedule_in.bus_id) | (Schedule.driver_id == schedule_in.driver_id)
        ).filter(
            (Schedule.start <= schedule_in.end) & (Schedule.end >= schedule_in.start)
        ).all()


schedule = CRUDSchedule(Schedule)
