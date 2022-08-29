from datetime import datetime, timedelta

import pytz

from app.schemas.Calendar import Calendar
from app.schemas.Schedule import ScheduleSchema, ScheduleDb

utc = pytz.UTC


def test_calendar_availability(dummy_payload, dummy_db_schedule_list):
    payload = ScheduleSchema(**dummy_payload)
    calendar = Calendar()
    calendar.schedules = dummy_db_schedule_list

    result = calendar.check_availability(
        start_dt=payload.start_dt,
        end_dt=payload.end_dt,
        driver_id=payload.driver_id,
        bus_id=payload.bus_id
    )

    assert result is True


def test_calendar_availability_w_conflict_time_schedule():
    payload = ScheduleSchema(
        bus_id=1,
        driver_id=1,
        start_dt=utc.localize(datetime.utcnow()),
        end_dt=utc.localize(datetime.utcnow() + timedelta(minutes=10))
    )

    calendar = Calendar()
    calendar.schedules = [ScheduleDb(
        id=1,
        bus_id=1,
        driver_id=1,
        start_dt=datetime.utcnow(),
        end_dt=datetime.utcnow() + timedelta(minutes=10)
    )]

    result = calendar.check_availability(
        start_dt=payload.start_dt,
        end_dt=payload.end_dt,
        driver_id=payload.driver_id,
        bus_id=payload.bus_id
    )

    assert result is False
