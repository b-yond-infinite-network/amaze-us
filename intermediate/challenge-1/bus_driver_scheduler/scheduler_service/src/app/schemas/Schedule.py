from pydantic import BaseModel, Field
from datetime import date, datetime


class ScheduleSchema(BaseModel):
    bus_id: int = Field(gt=0)
    driver_id: int = Field(gt=0)
    start_dt: datetime
    end_dt: datetime


class ScheduleDb(ScheduleSchema):
    id: int

    class Config:
        orm_mode = True
