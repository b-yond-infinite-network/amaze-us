import pytest
from fastapi import status
from datetime import datetime, date, time, timedelta

from src.exceptions.app_exception import AppException
from src.exceptions.db_exception import DBException
from src.models import Driver, Bus
from src.services.schedule_service import schedule
from src.database.db import ActiveSession
from src.schemas import ScheduleCreate, ScheduleUpdate
from tests.conftest import create_bus

given = pytest.mark.parametrize
STRING_DATE_FORMAT = "%Y-%m-%d"
STRING_TIME_FORMAT = "%H:%M:%S"


@pytest.mark.usefixtures()
class TestScheduleService:
    """
    Test class responsible to execute unit test cases in schedule service
    """

    @given("response_len", (0,))
    def test_get_all_schedules(self, db: ActiveSession, response_len: int):
        """
        Should return a list with all schedules in the database, limited to 100
        """
        schedules = schedule.get_all(db_session=db)
        assert schedules is not None
        assert len(schedules) > response_len

    @given("total_schedules, day", [(3, date(2022, 11, 1))])
    def test_get_all_schedules_for_a_driver_given_a_day(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        day: date,
    ):
        """
        Should be able to get all driver's schedule given the driver id
        Should return a list of schedules belonged to the driver
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        for i in range(8, 11):
            new_schedule = ScheduleCreate(
                day=day,
                start_hour=time(hour=i, minute=0, second=0),
                end_hour=time(hour=i, minute=30, second=0),
                driver_id=new_driver.id,
                bus_id=new_bus.id,
            )
            schedule.create(db_session=db, input_object=new_schedule)

        schedules = schedule.get_all_driver_day_schedules(
            db_session=db, driver_id=new_driver.id, day=day
        )

        assert schedules is not None
        assert len(schedules) == total_schedules

    @given("total_schedules, day", [(3, date(2022, 10, 1))])
    def test_get_all_schedules_for_a_bus_given_a_day(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        day: date,
    ):
        """
        Should be able to get all buses schedule given the bus id
        Should return a list of schedules belonged to the bus
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        for i in range(8, 11):
            new_schedule = ScheduleCreate(
                day=day,
                start_hour=time(hour=i, minute=0, second=0),
                end_hour=time(hour=i, minute=30, second=0),
                driver_id=new_driver.id,
                bus_id=new_bus.id,
            )
            schedule.create(db_session=db, input_object=new_schedule)

        schedules = schedule.get_all_bus_day_schedules(
            db_session=db, bus_id=new_bus.id, day=day
        )

        assert schedules is not None
        assert len(schedules) == total_schedules

    @given(
        "total_schedules, from_date, to_date",
        [(8, date(2022, 12, 1), date(2022, 12, 15))],
    )
    def test_get_all_schedules_for_a_driver_given_a_week(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        from_date: date,
        to_date: date,
    ):
        """
        Should be able to get all driver's schedule given the driver id
        Should return a list of schedules belonged to the driver
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        for i in range(3, 11):
            new_schedule = ScheduleCreate(
                day=date(year=2022, month=12, day=i),
                start_hour=time(hour=8, minute=0, second=0),
                end_hour=time(hour=15, minute=30, second=0),
                driver_id=new_driver.id,
                bus_id=new_bus.id,
            )
            schedule.create(db_session=db, input_object=new_schedule)

        schedules = schedule.get_all_driver_week_schedules(
            db_session=db,
            driver_id=new_driver.id,
            from_date=from_date,
            to_date=to_date,
        )

        assert schedules is not None
        assert len(schedules) == total_schedules

    @given(
        "total_schedules, from_date, to_date",
        [(8, date(2022, 9, 1), date(2022, 9, 15))],
    )
    def test_get_all_schedules_for_a_bus_given_a_week(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        from_date: date,
        to_date: date,
    ):
        """
        Should be able to get all bus schedules given the bus id
        Should return a list of schedules belonged to the bus
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        for i in range(3, 11):
            new_schedule = ScheduleCreate(
                day=date(year=2022, month=9, day=i),
                start_hour=time(hour=8, minute=0, second=0),
                end_hour=time(hour=15, minute=30, second=0),
                driver_id=new_driver.id,
                bus_id=new_bus.id,
            )
            schedule.create(db_session=db, input_object=new_schedule)

        schedules = schedule.get_all_bus_week_schedules(
            db_session=db,
            bus_id=new_bus.id,
            from_date=from_date,
            to_date=to_date,
        )

        assert schedules is not None
        assert len(schedules) == total_schedules

    @given(
        "day, start_hour, end_hour",
        [
            (
                date(year=2022, month=8, day=10),
                time(hour=8, minute=0, second=0),
                time(hour=11, minute=0, second=0),
            )
        ],
    )
    def test_get_schedule_by_id(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: date,
        start_hour: time,
        end_hour: time,
    ):
        """
        Should return a schedule given a valid id
        Should return the schedule object
        """
        new_driver = create_new_driver
        new_bus = create_new_bus
        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )

        created_schedule = schedule.create(
            db_session=db, input_object=new_schedule
        )
        the_schedule = schedule.get(db_session=db, id=created_schedule.id)

        assert the_schedule is not None
        assert the_schedule.day == day
        assert the_schedule.start_hour == start_hour
        assert the_schedule.end_hour == end_hour
        assert the_schedule.driver_id == new_driver.id
        assert the_schedule.bus_id == new_bus.id

    @given(
        "day,start_hour,end_hour",
        [
            (
                date(year=2022, month=8, day=15),
                time(hour=8, minute=0, second=0),
                time(hour=11, minute=0, second=0),
            )
        ],
    )
    def test_update_a_schedule_given_an_id(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: date,
        start_hour: time,
        end_hour: time,
    ):
        """
        Should be able to update a schedule given a valid id
        """
        new_driver = create_new_driver
        new_bus = create_new_bus
        schedule_obj = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )
        new_schedule = schedule.create(
            db_session=db, input_object=schedule_obj
        )

        update_day = date(year=2022, month=8, day=21)
        update_end_hour = time(hour=19, minute=30, second=0)

        updatable_schedule = ScheduleUpdate(
            day=update_day, end_hour=update_end_hour
        )
        updated_schedule = schedule.update(
            db_session=db,
            db_obj=new_schedule,
            input_object=updatable_schedule,
        )

        assert updated_schedule.day == update_day
        assert updated_schedule.end_hour == update_end_hour
        assert updated_schedule.start_hour == start_hour
        assert updated_schedule.bus_id == new_bus.id
        assert updated_schedule.driver_id == new_driver.id

    @given(
        "schedule_id, update_day, error_message, error_details, status_code",
        [
            (
                1231938,
                date(year=2022, month=9, day=20),
                "The requested Schedule updatable object does not exists",
                "The given id probably does not exist in the database",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            ),
            (
                23213218,
                date(year=2022, month=10, day=20),
                "The requested Schedule updatable object does not exists",
                "The given id probably does not exist in the database",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            ),
        ],
    )
    def test_update_schedule_with_non_existent_id(
        self,
        db: ActiveSession,
        schedule_id: int,
        update_day: date,
        error_message: str,
        error_details: str,
        status_code: status,
    ):
        """
        Should not be able to update a schedule given an invalid id
        Should return an AppException with internal server error status code
        """
        the_invalid_schedule = schedule.get(db_session=db, id=schedule_id)

        updatable_schedule = ScheduleUpdate(day=update_day)

        with pytest.raises(AppException) as exc:
            schedule.update(
                db_session=db,
                db_obj=the_invalid_schedule,
                input_object=updatable_schedule,
            )
        assert exc.value.error is True
        assert exc.value.message == error_message
        assert exc.value.status_code == status_code
        assert exc.value.details == error_details

    @given(
        "driver_id,error_message,status_code,is_foreignkey_error",
        [
            (
                12304321,
                "An error occurred while trying to save the entity's foreign key",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
                True,
            ),
            (
                9887809,
                "An error occurred while trying to save the entity's foreign key",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
                True,
            ),
        ],
    )
    def test_update_schedule_with_non_existent_driver_id(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        driver_id: int,
        error_message: str,
        status_code: status,
        is_foreignkey_error: bool,
    ):
        """
        Should not be able to update a schedule with an invalid driver id
        Should return a DBException with Internal server error status code
        """

        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule_obj = ScheduleCreate(
            day=(datetime.now() + timedelta(days=6))
            .date()
            .strftime(STRING_DATE_FORMAT),
            start_hour=datetime.now().time().strftime(STRING_TIME_FORMAT),
            end_hour=(datetime.now() + timedelta(hours=9))
            .time()
            .strftime(STRING_TIME_FORMAT),
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )
        the_schedule = schedule.create(
            db_session=db, input_object=new_schedule_obj
        )

        updatable_schedule = ScheduleUpdate()
        updatable_schedule.driver_id = driver_id

        with pytest.raises(DBException) as exc:
            schedule.update(
                db_session=db,
                db_obj=the_schedule,
                input_object=updatable_schedule,
            )
        assert exc.value.error is True
        assert exc.value.fk_error is is_foreignkey_error
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "day, start_hour, end_hour",
        [
            (
                datetime.now().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
            )
        ],
    )
    def test_create_new_schedule(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: str,
        start_hour: str,
        end_hour: str,
    ):
        """
        Should be able to create a new schedule
        Should return the created schedule object
        """
        new_driver = create_new_driver
        new_bus = create_new_bus
        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )

        created_schedule = schedule.create(
            db_session=db, input_object=new_schedule
        )

        assert created_schedule is not None
        assert created_schedule.day.strftime(STRING_DATE_FORMAT) == day
        assert (
            created_schedule.start_hour.strftime(STRING_TIME_FORMAT)
            == start_hour
        )
        assert (
            created_schedule.end_hour.strftime(STRING_TIME_FORMAT) == end_hour
        )
        assert created_schedule.driver_id == new_driver.id
        assert created_schedule.bus_id == new_bus.id

    @given(
        "day, start_hour, end_hour, error_message, status_code",
        [
            (
                (datetime.now() + timedelta(days=30)).strftime(
                    STRING_DATE_FORMAT
                ),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
                "Driver already has a schedule with this same bus on this date",
                status.HTTP_409_CONFLICT,
            )
        ],
    )
    def test_create_the_same_schedule_twice(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: str,
        start_hour: str,
        end_hour: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a same schedule twice
        Should return an AppException with conflict status code
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )
        created_schedule = schedule.create(
            db_session=db, input_object=new_schedule
        )

        new_schedule = ScheduleCreate(
            day=created_schedule.day,
            start_hour=created_schedule.start_hour,
            end_hour=created_schedule.end_hour,
            driver_id=created_schedule.driver_id,
            bus_id=created_schedule.bus_id,
        )

        with pytest.raises(AppException) as exc:
            schedule.create(db_session=db, input_object=new_schedule)

        assert exc.value.error is True
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "day, start_hour, end_hour, fake_driver_id, error_message, status_code, is_foreignkey_error",
        [
            (
                datetime.now().date().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
                231231,
                "An error occurred while trying to save the entity's foreign key",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
                True,
            )
        ],
    )
    def test_create_schedule_with_non_existent_driver_id(
        self,
        db: ActiveSession,
        create_new_bus: Bus,
        day: str,
        start_hour: str,
        end_hour: str,
        fake_driver_id: int,
        error_message: str,
        status_code: status,
        is_foreignkey_error: bool,
    ):
        """
        Should not be able to create a schedule with an invalid driver id
        Should return a DBException with Internal server error status
        """
        new_bus = create_new_bus
        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=fake_driver_id,
            bus_id=new_bus.id,
        )

        with pytest.raises(DBException) as exc:
            schedule.create(db_session=db, input_object=new_schedule)

        assert exc.value.error is True
        assert exc.value.fk_error is is_foreignkey_error
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "day, start_hour, end_hour, fake_bus_id, error_message, status_code",
        [
            (
                datetime.now().date().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
                221312,
                "The bus does not exists",
                status.HTTP_400_BAD_REQUEST,
            )
        ],
    )
    def test_create_schedule_with_non_existent_bus_id(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        day: str,
        start_hour: str,
        end_hour: str,
        fake_bus_id: int,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a schedule with an invalid bus id
        Should return an AppException with bad request status code
        """
        new_driver = create_new_driver

        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=fake_bus_id,
        )

        with pytest.raises(AppException) as exc:
            schedule.create(db_session=db, input_object=new_schedule)

        assert exc.value.error is True
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "day, start_hour, end_hour, error_message, status_code",
        [
            (
                (datetime.now() + timedelta(days=3))
                .date()
                .strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
                "Driver already has a schedule with this same bus on this date",
                status.HTTP_409_CONFLICT,
            )
        ],
    )
    def test_create_schedule_for_same_driver_in_same_date_and_time(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: str,
        start_hour: str,
        end_hour: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a schedule for a driver in the same day and time
         when he already had another one
        Should return an AppException with Conflict status code
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )

        new_db_schedule = schedule.create(
            db_session=db, input_object=new_schedule
        )
        assert new_db_schedule.id is not None
        assert new_db_schedule.driver_id == new_driver.id
        assert new_db_schedule.bus_id == new_bus.id
        assert new_db_schedule.day.strftime(STRING_DATE_FORMAT) == day
        assert (
            new_db_schedule.start_hour.strftime(STRING_TIME_FORMAT)
            == start_hour
        )
        assert (
            new_db_schedule.end_hour.strftime(STRING_TIME_FORMAT) == end_hour
        )

        fail_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )

        with pytest.raises(AppException) as exc:
            schedule.create(db_session=db, input_object=fail_schedule)

        assert exc.value.error is True
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "day, start_hour, end_hour, error_message, status_code",
        [
            (
                (datetime.now() + timedelta(days=3))
                .date()
                .strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=8))
                .time()
                .strftime(STRING_TIME_FORMAT),
                "Driver already has another schedule with another bus in the same date and time",
                status.HTTP_409_CONFLICT,
            )
        ],
    )
    def test_create_schedule_for_same_driver_inside_the_time(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: str,
        start_hour: str,
        end_hour: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a schedule for a driver in the same day and time when
        he already had another one
        Should return an AppException with Conflict status code
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=new_bus.id,
        )

        new_db_schedule = schedule.create(
            db_session=db, input_object=new_schedule
        )
        assert new_db_schedule.id is not None
        assert new_db_schedule.driver_id == new_driver.id
        assert new_db_schedule.bus_id == new_bus.id
        assert new_db_schedule.day.strftime(STRING_DATE_FORMAT) == day
        assert (
            new_db_schedule.start_hour.strftime(STRING_TIME_FORMAT)
            == start_hour
        )
        assert (
            new_db_schedule.end_hour.strftime(STRING_TIME_FORMAT) == end_hour
        )

        bus2 = create_bus(db)
        fail_schedule = ScheduleCreate(
            day=day,
            start_hour=start_hour,
            end_hour=end_hour,
            driver_id=new_driver.id,
            bus_id=bus2.id,
        )

        with pytest.raises(AppException) as exc:
            schedule.create(db_session=db, input_object=fail_schedule)

        assert exc.value.error is True
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    def test_remove_a_schedule(
        self, db: ActiveSession, create_new_driver: Driver, create_new_bus: Bus
    ):
        """
        Should be able to remove a schedule given a valid id
        Should return the removed object
        """
        new_bus = create_new_bus
        new_driver = create_new_driver
        schedule_obj = ScheduleCreate(
            day=datetime.now().date().strftime(STRING_DATE_FORMAT),
            start_hour=datetime.now().time().strftime(STRING_TIME_FORMAT),
            end_hour=(datetime.now() + timedelta(hours=7))
            .time()
            .strftime(STRING_TIME_FORMAT),
            bus_id=new_bus.id,
            driver_id=new_driver.id,
        )

        the_schedule = schedule.create(
            db_session=db, input_object=schedule_obj
        )
        remove = schedule.remove(db_session=db, id=the_schedule.id)
        assert remove.id == the_schedule.id
        has_removed = schedule.get(db_session=db, id=the_schedule.id)
        assert has_removed is None

    @given(
        "invalid_schedule_id, error_message, error_details, status_code",
        [
            (
                12312,
                "The requested Schedule object does not exists with this id",
                "The informed object with id 12312 probably does not exist in the database",
                status.HTTP_400_BAD_REQUEST,
            )
        ],
    )
    def test_remove_schedule_given_invalid_id(
        self,
        db: ActiveSession,
        invalid_schedule_id: int,
        error_message: str,
        error_details: str,
        status_code: status,
    ):
        """
        Should not be able to remove a schedule given an invalid id
        Should return an AppException with Bad request status code
        """
        with pytest.raises(AppException) as exc:
            schedule.remove(db_session=db, id=invalid_schedule_id)
        assert exc.value.message == error_message
        assert exc.value.details == error_details
        assert exc.value.status_code == status_code
        assert exc.value.error is True
