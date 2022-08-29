from datetime import datetime
from typing import List
from app.schemas.Schedule import ScheduleSchema
import pytz

utc = pytz.UTC


class Calendar:
    schedules: List[ScheduleSchema] = []

    def check_availability(self, start_dt: datetime, end_dt: datetime, driver_id: int, bus_id: int) -> bool:
        """
        Check the availability time of the schedules.
        :param start_dt: The start date of a new schedule.
        :param end_dt: The end date of a new schedule.
        :param driver_id: The driver id to check.
        :param bus_id: The bus id to check.
        :return: True or False if the new shift schedule is available.
        """
        for schedule in self.schedules:
            schedule_start = utc.localize(schedule.start_dt)
            schedule_end = utc.localize(schedule.end_dt)

            # Check time availability when the driver use the same bus
            if driver_id == schedule.driver_id and bus_id == schedule.bus_id:
                if (schedule_start <= start_dt <= schedule_end) or (schedule_start <= end_dt <= schedule_end):
                    return False

            # Check time availability when the driver try to drive another bus on the same time. Or try to use a bus
            # on the same time
            if driver_id == schedule.driver_id or bus_id == schedule.bus_id:
                if (schedule_start <= start_dt <= schedule_end) or (schedule_start <= end_dt <= schedule_end):
                    return False
        return True
