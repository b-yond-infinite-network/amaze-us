from datetime import date

from fastapi import Query
from pydantic import BaseModel


class TopDriversFilterParameters(BaseModel):
    weekDateBegin: date = Query(
        description="Any day of the desired week. The result will return all schedules between the begin of this week and the end of weekDateEnd.")
    weekDateEnd: date = Query(
        description="Any day of the desired week. The result will return all schedules between the begin of the weekDateBegin and the end of this week.")
