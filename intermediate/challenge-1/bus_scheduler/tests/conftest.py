import os
import random
import sys
import pytest
from fastapi.testclient import TestClient
from datetime import datetime, timedelta
from typer.testing import CliRunner

# This next line ensures tests uses its own database and settings environment
os.environ["FORCE_ENV_FOR_DYNACONF"] = "testing"  # noqa
# WARNING: Ensure imports from `fastapi_project_template` comes after this line

from src.models import Schedule, User
from src.schemas import ScheduleCreate, UserCreate
from src.services import driver, bus, schedule
from src.database.db import get_session, ActiveSession
from src.schemas.bus_schema import Bus, BusCreate
from src.schemas.driver_schema import DriverCreate, Driver
from src.services.user_service import user


from src.main import app  # noqa
from src.config import settings  # noqa
from src.database.db import StartDB  # noqa
from src.cli import cli  # noqa

db = next(get_session())
STRING_DATE_FORMAT = "%Y-%m-%d"
STRING_TIME_FORMAT = "%H:%M:%S"


# each test runs on cwd to its temp dir
@pytest.fixture(autouse=True)
def go_to_tmpdir(request):
    # Get the fixture dynamically by its name.
    tmpdir = request.getfixturevalue("tmpdir")
    # ensure local test created packages can be imported
    sys.path.insert(0, str(tmpdir))
    # Chdir only for the duration of the test.
    with tmpdir.as_cwd():
        yield


def remove_db():
    try:
        os.remove("bus_scheduler_testing.db")
    except FileNotFoundError:
        pass


@pytest.fixture(scope="session", autouse=True)
def initialize_db(request):
    StartDB(seed=True)
    request.addfinalizer(remove_db)


@pytest.fixture(scope="session", autouse=True)
def api_client():
    return TestClient(app=app)


@pytest.fixture(scope="function", autouse=True)
def db() -> ActiveSession:
    yield next(get_session())


@pytest.fixture(scope="function")
def create_new_driver(db) -> Driver:
    return create_driver(db)


def create_driver(db) -> Driver:
    new_driver_obj = DriverCreate(
        first_name=f"{random.randint(0, 1000)}_schedule_fake_driver_{random.randint(0, 1000)}",
        last_name=f"{random.randint(0, 1000)}_schedule_fake_driver_{random.randint(0, 1000)}",
        ssn=f"{random.randint(0, 1000)}_schedule_fake_ssn_{random.randint(0, 1000)}",
        email=f"{random.randint(0, 1000)}_schedule.fake{random.randint(0, 1000)}@driver.com",
    )

    return driver.create(db_session=db, input_object=new_driver_obj)


@pytest.fixture(scope="function")
def create_new_bus(db) -> Bus:
    return create_bus(db)


def create_bus(db) -> Bus:
    new_bus_obj = BusCreate(
        model=f"{random.randint(0, 1000)}_fake_schedule_model_{random.randint(0, 1000)}",
        make=f"{random.randint(0, 1000)}_fake_schedule_make_{random.randint(0, 1000)}",
        capacity=random.randint(0, 50),
    )

    return bus.create(db_session=db, input_object=new_bus_obj)


@pytest.fixture(scope="function")
def create_new_schedule(db, create_new_driver, create_new_bus) -> Schedule:
    rand_day = random.randint(1, 30)
    rand_hour = random.randint(1, 10)

    day = (
        (datetime.now() + timedelta(days=rand_day))
        .date()
        .strftime(STRING_DATE_FORMAT)
    )
    start_hour = datetime.now().time().strftime(STRING_TIME_FORMAT)
    end_hour = (
        (datetime.now() + timedelta(hours=rand_hour))
        .time()
        .strftime(STRING_TIME_FORMAT)
    )

    new_driver = create_new_driver
    new_bus = create_new_bus

    new_schedule = ScheduleCreate(
        day=day,
        start_hour=start_hour,
        end_hour=end_hour,
        driver_id=new_driver.id,
        bus_id=new_bus.id,
    )

    return schedule.create(db_session=db, input_object=new_schedule)


@pytest.fixture(scope="function")
def create_user(db) -> User:

    new_user = UserCreate(
        first_name=f"{random.randint(0, 1000)}_fake_user_{random.randint(0, 1000)}",
        last_name=f"{random.randint(0, 1000)}_fake_last_name{random.randint(0, 1000)}",
        email=f"{random.randint(0, 1000)}_user.fake{random.randint(0, 1000)}@user.com",
        is_superuser=bool(random.getrandbits(1)),
        password="fake_pass",
    )

    return user.create(db_session=db, input_object=new_user)


@pytest.fixture(scope="function", name="cli")
def _cli():
    return cli


@pytest.fixture(scope="function")
def auth_super_user(api_client):
    data = {
        "grant_type": "password",
        "username": "admin@admin.com",
        "password": "123456",
    }

    response = api_client.post("/api/v1/users/login", data=data).json()

    return {
        "Authorization": f"{response['token_type'].capitalize()} {response['access_token']}"
    }


@pytest.fixture(scope="function")
def auth_user(api_client):
    data = {
        "grant_type": "password",
        "username": "user@user.com",
        "password": "123456",
    }

    response = api_client.post("/api/v1/users/login", data=data).json()

    return {
        "Authorization": f"{response['token_type'].capitalize()} {response['access_token']}"
    }


# @pytest.fixture(scope="function", name="settings")
# def _settings():
#     return settings

# @pytest.fixture(scope="function")
# def api_client_authenticated():
#     try:
#         create_user("admin", "admin", superuser=True)
#     except IntegrityError:
#         pass
#
#     client = TestClient(app)
#     token = client.post(
#         "/token",
#         data={"username": "admin", "password": "admin"},
#         headers={"Content-Type": "application/x-www-form-urlencoded"},
#     ).json()["access_token"]
#     client.headers["Authorization"] = f"Bearer {token}"
#     return client


@pytest.fixture(scope="function")
def cli_client():
    return CliRunner()
