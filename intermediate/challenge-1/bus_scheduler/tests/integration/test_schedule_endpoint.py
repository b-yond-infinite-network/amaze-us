import json
import pytest
from datetime import datetime, timedelta, date, time

from fastapi import status
from fastapi.testclient import TestClient

from src.models import Schedule, Bus, Driver

given = pytest.mark.parametrize
SCHEDULES_PATH = "/api/v1/schedules/"
STRING_DATE_FORMAT = "%Y-%m-%d"
STRING_TIME_FORMAT = "%H:%M:%S"


class TestScheduleEndpoint:
    """
    Test class responsible to execute integration test cases in schedule endpoints
    """

    @given("response_len, status_code", [(0, status.HTTP_200_OK)])
    def test_get_all_schedules(
        self, api_client: TestClient, response_len: int, status_code: status
    ):
        """
        Should return a list with all schedules if it exists on database
        """
        response = api_client.get(SCHEDULES_PATH)
        assert response.status_code == status_code
        assert isinstance(response.json(), list)
        assert len(response.json()) > response_len

    @given("status_code", (status.HTTP_200_OK,))
    def test_get_schedule_given_an_id(
        self,
        api_client: TestClient,
        create_new_schedule: Schedule,
        status_code: status,
    ):
        """
        Should return a list with a schedule given a valid id if it exists on database
        """
        new_schedule = create_new_schedule
        response = api_client.get(f"{SCHEDULES_PATH}{new_schedule.id}")

        assert response.status_code == status_code
        assert response.json()["day"] == new_schedule.day.strftime(
            STRING_DATE_FORMAT
        )
        assert response.json()[
            "start_hour"
        ] == new_schedule.start_hour.strftime(STRING_TIME_FORMAT)
        assert response.json()["driver_id"] == new_schedule.driver_id
        assert response.json()["bus_id"] == new_schedule.bus_id

    @given("total_schedules, day", [(3, date(2022, 11, 1))])
    def test_get_all_schedules_for_a_driver_given_a_day(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        day: date,
    ):
        """
        Should be able to get all driver's schedule given the driver id
        Should return a list of schedules belonged to the driver
        Should return No content status code if there are no schedules in the database
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        schedules = api_client.get(
            f"{SCHEDULES_PATH}drivers/{new_driver.id}/day/{day}"
        )
        assert schedules.status_code == status.HTTP_204_NO_CONTENT

        for i in range(8, 11):
            new_schedule = {
                "day": day.strftime(STRING_DATE_FORMAT),
                "start_hour": time(hour=i, minute=0, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "end_hour": time(hour=i, minute=30, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "driver_id": new_driver.id,
                "bus_id": new_bus.id,
            }
            api_client.post(
                SCHEDULES_PATH,
                data=json.dumps(new_schedule),
                headers=auth_super_user,
            )

        schedules = api_client.get(
            f"{SCHEDULES_PATH}drivers/{new_driver.id}/day/{day}"
        )

        assert schedules is not None
        assert schedules.status_code == status.HTTP_200_OK
        assert len(schedules.json()) == total_schedules

    @given("total_schedules, day", [(3, date(2022, 10, 1))])
    def test_get_all_schedules_for_a_bus_given_a_day(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        day: date,
    ):
        """
        Should be able to get all buses schedule given the bus id
        Should return a list of schedules belonged to the bus
        Should return No content status code if there are no schedules in the database
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        schedules = api_client.get(
            f"{SCHEDULES_PATH}buses/{new_bus.id}/day/{day}"
        )
        assert schedules.status_code == status.HTTP_204_NO_CONTENT

        for i in range(8, 11):
            new_schedule = {
                "day": day.strftime(STRING_DATE_FORMAT),
                "start_hour": time(hour=i, minute=0, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "end_hour": time(hour=i, minute=30, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "driver_id": new_driver.id,
                "bus_id": new_bus.id,
            }
            api_client.post(
                SCHEDULES_PATH,
                data=json.dumps(new_schedule),
                headers=auth_super_user,
            )

        schedules = api_client.get(
            f"{SCHEDULES_PATH}buses/{new_bus.id}/day/{day}"
        )

        assert schedules is not None
        assert schedules.status_code == status.HTTP_200_OK
        assert len(schedules.json()) == total_schedules

    @given(
        "total_schedules, from_date, to_date",
        [(8, date(2022, 12, 1), date(2022, 12, 15))],
    )
    def test_get_all_schedules_for_a_driver_given_a_week(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        from_date: date,
        to_date: date,
    ):
        """
        Should be able to get all driver's schedule given the driver id
        Should return a list of schedules belonged to the driver
        Should return No content status code if there are no schedules in the database
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        schedules = api_client.get(
            f"{SCHEDULES_PATH}drivers/{new_driver.id}/week?from_date={from_date}&to_date={to_date}"
        )
        assert schedules.status_code == status.HTTP_204_NO_CONTENT

        for i in range(3, 11):
            new_schedule = {
                "day": date(year=2022, month=12, day=i).strftime(
                    STRING_DATE_FORMAT
                ),
                "start_hour": time(hour=8, minute=0, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "end_hour": time(hour=15, minute=30, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "driver_id": new_driver.id,
                "bus_id": new_bus.id,
            }
            api_client.post(
                SCHEDULES_PATH,
                data=json.dumps(new_schedule),
                headers=auth_super_user,
            )

        schedules = api_client.get(
            f"{SCHEDULES_PATH}drivers/{new_driver.id}/week?from_date={from_date}&to_date={to_date}"
        )

        assert schedules is not None
        assert schedules.status_code == status.HTTP_200_OK
        assert len(schedules.json()) == total_schedules

    @given(
        "total_schedules, from_date, to_date",
        [(8, date(2022, 9, 1), date(2022, 9, 15))],
    )
    def test_get_all_schedules_for_a_bus_given_a_week(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        total_schedules: int,
        from_date: date,
        to_date: date,
    ):
        """
        Should be able to get all bus schedules given the bus id
        Should return a list of schedules belonged to the bus
        Should return No content status code if there are no schedules in the database
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        schedules = api_client.get(
            f"{SCHEDULES_PATH}buses/{new_bus.id}/week?from_date={from_date}&to_date={to_date}"
        )
        assert schedules.status_code == status.HTTP_204_NO_CONTENT

        for i in range(3, 11):
            new_schedule = {
                "day": date(year=2022, month=9, day=i).strftime(
                    STRING_DATE_FORMAT
                ),
                "start_hour": time(hour=8, minute=0, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "end_hour": time(hour=15, minute=30, second=0).strftime(
                    STRING_TIME_FORMAT
                ),
                "driver_id": new_driver.id,
                "bus_id": new_bus.id,
            }
            api_client.post(
                SCHEDULES_PATH,
                data=json.dumps(new_schedule),
                headers=auth_super_user,
            )

        schedules = api_client.get(
            f"{SCHEDULES_PATH}buses/{new_bus.id}/week?from_date={from_date}&to_date={to_date}"
        )

        assert schedules is not None
        assert schedules.status_code == status.HTTP_200_OK
        assert len(schedules.json()) == total_schedules

    @given("schedule_id, status_code", [(9093213, status.HTTP_204_NO_CONTENT)])
    def test_get_schedule_invalid_id(
        self, api_client: TestClient, schedule_id: int, status_code: status
    ):
        """
        Should return no content when trying to get a schedule with invalid or not existent id
        """
        response = api_client.get(f"{SCHEDULES_PATH}{schedule_id}")
        assert response.status_code == status_code

    @given("status_code", (status.HTTP_204_NO_CONTENT,))
    def test_no_schedules_in_database(
        self, auth_super_user, api_client: TestClient, status_code: status
    ):
        """
        Should return no content when there are no schedules in the database
        """
        response = api_client.get(SCHEDULES_PATH)

        for item in response.json():
            schedule_id = item["id"]
            api_client.delete(
                f"{SCHEDULES_PATH}{schedule_id}", headers=auth_super_user
            )

        response = api_client.get(SCHEDULES_PATH)
        assert response.status_code == status_code

    @given(
        "day,start_hour,end_hour,status_code",
        [
            (
                datetime.now().date().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=9))
                .time()
                .strftime(STRING_TIME_FORMAT),
                status.HTTP_201_CREATED,
            )
        ],
    )
    def test_create_new_schedule(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: int,
        start_hour: str,
        end_hour: str,
        status_code: status,
    ):
        """
        Should return created http status and the new object in response
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule = {
            "day": day,
            "start_hour": start_hour,
            "end_hour": end_hour,
            "driver_id": new_driver.id,
            "bus_id": new_bus.id,
        }

        response = api_client.post(
            SCHEDULES_PATH,
            data=json.dumps(obj=new_schedule),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert response.json()["day"] == day
        assert response.json()["start_hour"] == start_hour
        assert response.json()["end_hour"] == end_hour
        assert response.json()["driver_id"] == new_driver.id
        assert response.json()["bus_id"] == new_bus.id

    @given(
        "day,start_hour,end_hour,status_code",
        [
            (
                datetime.now().date().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=9))
                .time()
                .strftime(STRING_TIME_FORMAT),
                status.HTTP_401_UNAUTHORIZED,
            )
        ],
    )
    def test_create_new_schedule_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_driver: Driver,
        create_new_bus: Bus,
        day: int,
        start_hour: str,
        end_hour: str,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_driver = create_new_driver
        new_bus = create_new_bus

        new_schedule = {
            "day": day,
            "start_hour": start_hour,
            "end_hour": end_hour,
            "driver_id": new_driver.id,
            "bus_id": new_bus.id,
        }

        response = api_client.post(
            SCHEDULES_PATH,
            data=json.dumps(obj=new_schedule),
            headers=auth_user,
        )

        assert response.status_code == status_code

    @given(
        "day,start_hour,end_hour,driver_id,status_code",
        [
            (
                datetime.now().date().strftime(STRING_DATE_FORMAT),
                datetime.now().time().strftime(STRING_TIME_FORMAT),
                (datetime.now() + timedelta(hours=9))
                .time()
                .strftime(STRING_TIME_FORMAT),
                2112312,
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_a_schedule_with_non_existent_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_bus: Bus,
        day: int,
        start_hour: str,
        end_hour: str,
        driver_id: int,
        status_code: status,
    ):
        """
        Should return an error with internal server error http status and the information about this.
        """
        new_bus = create_new_bus
        new_schedule = {
            "day": day,
            "start_hour": start_hour,
            "end_hour": end_hour,
            "driver_id": driver_id,
            "bus_id": new_bus.id,
        }
        response = api_client.post(
            SCHEDULES_PATH,
            data=json.dumps(new_schedule),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert (
            response.json()["message"]
            == "An error occurred while trying to save the entity's foreign key"
        )

    @given(
        "day, start_hour, status_code",
        [
            (
                (datetime.now() + timedelta(days=5))
                .date()
                .strftime(STRING_DATE_FORMAT),
                (datetime.now() + timedelta(hours=4))
                .time()
                .strftime(STRING_TIME_FORMAT),
                status.HTTP_200_OK,
            )
        ],
    )
    def test_update_schedule(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_schedule: Schedule,
        day: str,
        start_hour: str,
        status_code: status,
    ):
        """
        Should return OK http status and the modified object.
        """
        new_schedule = create_new_schedule
        update_schedule_obj = {"day": day, "start_hour": start_hour}
        response = api_client.put(
            f"{SCHEDULES_PATH}{new_schedule.id}",
            data=json.dumps(update_schedule_obj),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert response.json()["id"] == new_schedule.id
        assert response.json()["end_hour"] == new_schedule.end_hour.strftime(
            STRING_TIME_FORMAT
        )
        assert response.json()["driver_id"] == new_schedule.driver_id

        assert response.json()["day"] != new_schedule.day.strftime(
            STRING_DATE_FORMAT
        )
        assert response.json()[
            "start_hour"
        ] != new_schedule.start_hour.strftime(STRING_TIME_FORMAT)

    @given(
        "day, start_hour, status_code",
        [
            (
                (datetime.now() + timedelta(days=5))
                .date()
                .strftime(STRING_DATE_FORMAT),
                (datetime.now() + timedelta(hours=4))
                .time()
                .strftime(STRING_TIME_FORMAT),
                status.HTTP_401_UNAUTHORIZED,
            )
        ],
    )
    def test_update_schedule_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_schedule: Schedule,
        day: str,
        start_hour: str,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_schedule = create_new_schedule
        update_schedule_obj = {"day": day, "start_hour": start_hour}

        response = api_client.put(
            f"{SCHEDULES_PATH}{new_schedule.id}",
            data=json.dumps(update_schedule_obj),
            headers=auth_user,
        )

        assert response.status_code == status_code

    @given(
        "schedule_id, capacity, model, error_message, status_code",
        [
            (
                1321,
                28,
                "Model Update Test",
                "The requested Schedule updatable object does not exists",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_non_existent_schedule(
        self,
        auth_super_user,
        api_client: TestClient,
        schedule_id: int,
        capacity: int,
        model: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should return an error with internal server error http status and the information about this.
        """
        new_schedule = {"capacity": capacity, "model": model}
        response = api_client.put(
            f"{SCHEDULES_PATH}{schedule_id}",
            data=json.dumps(new_schedule),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message

    @given(
        "delete_status_code, query_status_code",
        [(status.HTTP_200_OK, status.HTTP_204_NO_CONTENT)],
    )
    def test_remove_a_schedule(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_schedule: Schedule,
        delete_status_code: status,
        query_status_code: status,
    ):
        """
        Should return OK http status and the removed object
        """
        new_schedule = create_new_schedule
        response = api_client.delete(
            f"{SCHEDULES_PATH}{new_schedule.id}", headers=auth_super_user
        )
        assert response.status_code == delete_status_code

        was_removed = api_client.get(f"{SCHEDULES_PATH}{new_schedule.id}")
        assert was_removed.status_code == query_status_code

    @given("status_code", (status.HTTP_401_UNAUTHORIZED,))
    def test_remove_schedule_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_schedule: Schedule,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_schedule = create_new_schedule
        response = api_client.delete(
            f"{SCHEDULES_PATH}{new_schedule.id}", headers=auth_user
        )

        assert response.status_code == status_code

    @given(
        "schedule_id, status_code, error_message, details_message",
        [
            (
                1100,
                status.HTTP_400_BAD_REQUEST,
                "The requested Schedule object does not exists with this id",
                "The informed object with id 1100 probably does not exist in the database",
            )
        ],
    )
    def test_remove_schedule_with_invalid_id(
        self,
        auth_super_user,
        api_client: TestClient,
        schedule_id: int,
        status_code: status,
        error_message: str,
        details_message: str,
    ):
        """
        Should return an error with bad request http status and the information about this.
        """
        response = api_client.delete(
            f"{SCHEDULES_PATH}{schedule_id}", headers=auth_super_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message
        assert response.json()["details"] == details_message
        assert response.json()["error"] is True
