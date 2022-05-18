from typing import Optional
from datetime import date, time

from pydantic import validator, BaseModel


# Shared properties
class ScheduleBase(BaseModel):
    date: date
    start: time
    end: time
    bus_id: int
    driver_id: int

    @validator('end', always=True)
    @classmethod
    def validate_times(cls, v, values):
        if values["start"] > v:
            raise ValueError("The End field must be after the Start field")
        return v

    @validator('date', always=True)
    @classmethod
    def validate_date(cls, v):
        if v < date.today():
            raise ValueError("Choose a date from today")
        return v


# Properties to receive via API on creation
class ScheduleCreate(ScheduleBase):
    pass


# Properties to receive via API on update
class ScheduleUpdate(ScheduleBase):
    pass


class Schedule(ScheduleBase):
    id: int

    class Config:
        orm_mode = True


class ScheduleGet(BaseModel):
    date: date
    start: time
    end: time
    bus_id: Optional[int]
    driver_id: Optional[int]

    class Config:
        orm_mode = True
