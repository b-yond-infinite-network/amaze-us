from datetime import date

from fastapi import Query
from pydantic import BaseModel

class BusScheduleFilterParameters(BaseModel):
    weekDate: date = Query(description="Any day of the desired week. The result will return all schedules between the begin of the week of the day and the end of it week.")
