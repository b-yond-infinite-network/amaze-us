from typing import Optional
from datetime import datetime

from pydantic import BaseModel


class ScheduleBase(BaseModel):
    start: datetime
    end: datetime
    bus_id: int
    driver_id: int


class ScheduleCreate(ScheduleBase):
    pass


class ScheduleUpdate(ScheduleBase):
    pass


class Schedule(ScheduleBase):
    id: int

    class Config:
        orm_mode = True


class ScheduleGet(BaseModel):
    start: datetime
    end: datetime
    bus_id: Optional[int]
    driver_id: Optional[int]

    class Config:
        orm_mode = True
