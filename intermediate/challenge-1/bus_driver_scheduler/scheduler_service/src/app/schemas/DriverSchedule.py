from datetime import datetime

from pydantic import BaseModel, Field


class DriverSchedule(BaseModel):
    driver_id: int = Field(gt=0)
    start_dt: datetime
    end_dt: datetime

