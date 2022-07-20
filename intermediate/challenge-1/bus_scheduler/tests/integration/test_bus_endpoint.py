import json
import pytest

from fastapi import status
from fastapi.testclient import TestClient

from src.models import Driver, Bus

given = pytest.mark.parametrize
BUSES_PATH = "/api/v1/buses/"


@pytest.mark.usefixtures()
class TestBusEndpoint:
    """
    Test class responsible to execute integration test cases in bus endpoints
    """

    @given("response_len", (0,))
    def test_get_all_buses(self, api_client: TestClient, response_len: int):
        """
        Should return a list with all buses if it exists on database
        """
        response = api_client.get(BUSES_PATH)
        assert response.status_code == status.HTTP_200_OK
        assert isinstance(response.json(), list)
        assert len(response.json()) > response_len

    @given("status_code", (status.HTTP_200_OK,))
    def test_get_bus_given_an_id(
        self, api_client: TestClient, create_new_bus: Bus, status_code: status
    ):
        """
        Should return a list with a bus given a valid id if it exists on database
        """
        new_bus = create_new_bus
        response = api_client.get(f"{BUSES_PATH}{new_bus.id}")

        assert response.status_code == status_code
        assert response.json()["model"] == new_bus.model
        assert response.json()["make"] == new_bus.make

    @given("bus_id, status_code", [(9093213, status.HTTP_204_NO_CONTENT)])
    def test_get_bus_invalid_id(
        self, api_client: TestClient, bus_id: int, status_code: status
    ):
        """
        Should return no content when trying to get a bus with invalid or not existent id
        """
        response = api_client.get(f"{BUSES_PATH}{bus_id}")
        assert response.status_code == status_code

    @given("status_code", (status.HTTP_204_NO_CONTENT,))
    def test_no_buses_in_database(
        self, auth_super_user, api_client: TestClient, status_code: status
    ):
        """
        Should return no content when there are no buses in the database
        """
        response = api_client.get(BUSES_PATH)

        for item in response.json():
            bus_id = item["id"]
            api_client.delete(f"{BUSES_PATH}{bus_id}", headers=auth_super_user)

        response = api_client.get(BUSES_PATH)
        assert response.status_code == status_code

    @given(
        "capacity, model, make, status_code, driver_id",
        [(36, "Model Test", "Make Test", status.HTTP_201_CREATED, None)],
    )
    def test_create_new_bus(
        self,
        auth_super_user,
        api_client: TestClient,
        capacity: int,
        model: str,
        make: str,
        status_code: status,
        driver_id: int,
    ):
        """
        Should return created http status and the new object in response
        """
        new_bus = {"capacity": capacity, "model": model, "make": make}

        response = api_client.post(
            BUSES_PATH, data=json.dumps(obj=new_bus), headers=auth_super_user
        )

        assert response.status_code == status_code
        assert response.json()["capacity"] == capacity
        assert response.json()["model"] == model
        assert response.json()["make"] == make
        assert response.json()["driver_id"] == driver_id

    @given(
        "capacity, model, make, status_code, error_message",
        [
            (
                36,
                "Model Test",
                "Make Test",
                status.HTTP_401_UNAUTHORIZED,
                "Only system admins user can execute this operation",
            )
        ],
    )
    def test_create_new_bus_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        capacity: int,
        model: str,
        make: str,
        status_code: status,
        error_message: str,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_bus = {"capacity": capacity, "model": model, "make": make}

        response = api_client.post(
            BUSES_PATH, data=json.dumps(obj=new_bus), headers=auth_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message

    @given(
        "capacity, model, make, status_code",
        [(36, "Model Test - 2", "Make Test - 2", status.HTTP_201_CREATED)],
    )
    def test_create_a_bus_with_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        capacity: int,
        model: str,
        make: str,
        status_code: status,
    ):
        """
        Should return created http status and the new object in response
        """
        new_driver = create_new_driver
        new_bus = {
            "capacity": capacity,
            "model": model,
            "make": make,
            "driver_id": new_driver.id,
        }

        response = api_client.post(
            BUSES_PATH, data=json.dumps(new_bus), headers=auth_super_user
        )

        assert response.status_code == status_code
        assert response.json()["capacity"] == capacity
        assert response.json()["model"] == model
        assert response.json()["make"] == make
        assert response.json()["driver_id"] == new_driver.id

    @given(
        "capacity, model, make, status_code, error_message",
        [
            (
                36,
                "Model Test - 2",
                "Make Test - 2",
                status.HTTP_401_UNAUTHORIZED,
                "Only system admins user can execute this operation",
            )
        ],
    )
    def test_create_a_bus_with_driver_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_driver: Driver,
        capacity: int,
        model: str,
        make: str,
        status_code: status,
        error_message: str,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_driver = create_new_driver
        new_bus = {
            "capacity": capacity,
            "model": model,
            "make": make,
            "driver_id": new_driver.id,
        }

        response = api_client.post(
            BUSES_PATH, data=json.dumps(new_bus), headers=auth_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message

    @given(
        "capacity, model, make, driver_id, message, error",
        [
            (
                36,
                "Model Test - 3",
                "Make Test - 3",
                1312,
                "An error occurred while trying to save the entity's foreign key",
                True,
            )
        ],
    )
    def test_create_a_bus_with_non_existent_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        capacity: int,
        model: str,
        make: str,
        driver_id: int,
        message: str,
        error: bool,
    ):
        """
        Should return an error with internal server error http status and the information about this.
        """
        new_bus = {
            "capacity": capacity,
            "model": model,
            "make": make,
            "driver_id": driver_id,
        }
        response = api_client.post(
            BUSES_PATH, data=json.dumps(new_bus), headers=auth_super_user
        )

        assert response.status_code == status.HTTP_500_INTERNAL_SERVER_ERROR
        assert response.json()["message"] == message
        assert response.json()["error"] == error

    @given(
        "capacity, model, status_code",
        [(28, "Model Update Test", status.HTTP_200_OK)],
    )
    def test_update_bus(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_bus: Bus,
        capacity: int,
        model: str,
        status_code: status,
    ):
        """
        Should return OK http status and the modified object.
        """
        new_bus = create_new_bus
        update_bus_obj = {"capacity": capacity, "model": model}
        response = api_client.put(
            f"{BUSES_PATH}{new_bus.id}",
            data=json.dumps(update_bus_obj),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert response.json()["id"] == new_bus.id
        assert response.json()["make"] == new_bus.make
        assert response.json()["driver_id"] == new_bus.driver

        assert response.json()["model"] != new_bus.model
        assert response.json()["capacity"] != new_bus.capacity

    @given(
        "capacity, model, error_message, status_code",
        [
            (
                28,
                "Model Update Test",
                "Only system admins user can execute this operation",
                status.HTTP_401_UNAUTHORIZED,
            )
        ],
    )
    def test_update_bus_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_bus: Bus,
        capacity: int,
        model: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_bus = create_new_bus
        update_bus_obj = {"capacity": capacity, "model": model}

        response = api_client.put(
            f"{BUSES_PATH}{new_bus.id}",
            data=json.dumps(update_bus_obj),
            headers=auth_user,
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message

    @given(
        "bus_id, capacity, model, error_message",
        [
            (
                1321,
                28,
                "Model Update Test",
                "The requested Bus updatable object does not exists",
            )
        ],
    )
    def test_update_non_existent_bus(
        self,
        auth_super_user,
        api_client: TestClient,
        bus_id: int,
        capacity: int,
        model: str,
        error_message: str,
    ):
        """
        Should return an error with internal server error http status and the information about this.
        """
        new_bus = {"capacity": capacity, "model": model}
        response = api_client.put(
            f"{BUSES_PATH}{bus_id}",
            data=json.dumps(new_bus),
            headers=auth_super_user,
        )

        assert response.status_code == status.HTTP_500_INTERNAL_SERVER_ERROR
        assert response.json()["message"] == error_message

    @given(
        "delete_status_code, query_status_code",
        [(status.HTTP_200_OK, status.HTTP_204_NO_CONTENT)],
    )
    def test_remove_a_bus(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_bus: Bus,
        delete_status_code: status,
        query_status_code: status,
    ):
        """
        Should return OK http status and the removed object
        """
        new_bus = create_new_bus
        response = api_client.delete(
            f"{BUSES_PATH}{new_bus.id}", headers=auth_super_user
        )
        assert response.status_code == delete_status_code

        was_removed = api_client.get(
            f"{BUSES_PATH}{new_bus.id}", headers=auth_super_user
        )

        assert was_removed.status_code == query_status_code

    @given(
        "status_code, error_message",
        [
            (
                status.HTTP_401_UNAUTHORIZED,
                "Only system admins user can execute this operation",
            )
        ],
    )
    def test_remove_bus_as_non_superuser(
        self,
        auth_user,
        api_client: TestClient,
        create_new_bus: Bus,
        status_code: status,
        error_message: str,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_bus = create_new_bus
        response = api_client.delete(
            f"{BUSES_PATH}{new_bus.id}", headers=auth_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message

    @given(
        "bus_id, status_code, error_message, details_message",
        [
            (
                1100,
                status.HTTP_400_BAD_REQUEST,
                "The requested Bus object does not exists with this id",
                "The informed object with id 1100 probably does not exist in the database",
            )
        ],
    )
    def test_remove_bus_with_invalid_id(
        self,
        auth_super_user,
        api_client: TestClient,
        bus_id: int,
        status_code: status,
        error_message: str,
        details_message: str,
    ):
        """
        Should return an error with bad request http status and the information about this.
        """
        response = api_client.delete(
            f"{BUSES_PATH}{bus_id}", headers=auth_super_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message
        assert response.json()["details"] == details_message
        assert response.json()["error"] is True
