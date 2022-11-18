from datetime import datetime

from pydantic import BaseModel, Field

class ScheduleCreateModel(BaseModel):
    class Config:
        orm_mode = True

    driver_id: int = Field(default=None, title="Driver id")
    bus_id: int = Field(default=None, title="Bus id")
    begin: datetime = Field(default=None, title="Begin of the schedule")
    end: datetime = Field(default=None, title="End of the schedule")
