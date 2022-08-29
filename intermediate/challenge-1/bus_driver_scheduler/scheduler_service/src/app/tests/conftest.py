from datetime import datetime, timedelta, date
from json import JSONEncoder

import pytest
import pytz
from starlette.testclient import TestClient

from app.main import app
from app.schemas.Schedule import ScheduleDb


class DateTimeEncoder(JSONEncoder):
    # Override the default method
    def default(self, obj):
        if isinstance(obj, (date, datetime)):
            return obj.isoformat()


@pytest.fixture(scope="module")
def get_app():
    client = TestClient(app)
    yield client


@pytest.fixture(scope="session")
def dummy_payload():
    return {
        "bus_id": 1,
        "driver_id": 1,
        "start_dt": pytz.utc.localize(datetime.utcnow()),
        "end_dt": pytz.utc.localize(datetime.utcnow() + timedelta(minutes=10)),
    }


@pytest.fixture(scope="session")
def dummy_db_schedule():
    return ScheduleDb(
        id=1,
        bus_id=1,
        driver_id=1,
        start_dt=datetime.now(),
        end_dt=datetime.now() + timedelta(minutes=10)
    )


@pytest.fixture(scope="session")
def dummy_db_schedule_list():
    return [
        ScheduleDb(id=1, bus_id=1, driver_id=1, start_dt=datetime.now(), end_dt=datetime.now() + timedelta(minutes=10)),
        ScheduleDb(id=1, bus_id=1, driver_id=1, start_dt=datetime.now() + timedelta(hours=1), end_dt=datetime.now() + timedelta(hours=1, minutes=10)),
    ]
