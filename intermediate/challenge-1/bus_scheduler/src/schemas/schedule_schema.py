from datetime import date, time
from typing import Optional, Sequence

from pydantic import BaseModel


class ScheduleBase(BaseModel):
    day: date
    start_hour: time
    end_hour: time
    driver_id: int
    bus_id: int


class ScheduleUpdate(ScheduleBase):
    day: Optional[date]
    start_hour: Optional[time]
    end_hour: Optional[time]
    driver_id: Optional[int]
    bus_id: Optional[int]


class ScheduleCreate(ScheduleBase):
    ...


class ScheduleInDBBase(ScheduleBase):
    id: int

    class Config:
        orm_mode = True


class Schedule(ScheduleInDBBase):
    pass


# Properties properties stored in DB
class ScheduleInDB(ScheduleInDBBase):
    pass
