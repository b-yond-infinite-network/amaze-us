import json

import pytest
from fastapi import status
from fastapi.testclient import TestClient
from pydantic import EmailStr

from src.models.driver_model import Driver

given = pytest.mark.parametrize
DRIVERS_PATH = "/api/v1/drivers/"


@pytest.mark.usefixtures()
class TestDriverEndpoint:
    """
    Test class responsible to execute integration test cases in driver endpoints
    """

    @given("response_len", (0,))
    def test_get_all_drivers(self, api_client: TestClient, response_len: int):
        """
        Should return a list with all drivers if it exists on database
        """
        response = api_client.get(f"{DRIVERS_PATH}?skip=0&limit=50")
        assert response.status_code == status.HTTP_200_OK
        assert isinstance(response.json(), list)
        assert len(response.json()) > response_len

    @given("status_code", (status.HTTP_200_OK,))
    def test_get_driver_given_an_id(
        self,
        api_client: TestClient,
        create_new_driver: Driver,
        status_code: int,
    ):
        """
        Should return a list with a driver given a valid id if it exists on database
        """
        new_driver = create_new_driver
        response = api_client.get(f"{DRIVERS_PATH}{new_driver.id}")

        assert response.status_code == status_code
        assert response.json()["first_name"] == new_driver.first_name
        assert response.json()["last_name"] == new_driver.last_name
        assert response.json()["ssn"] == new_driver.ssn
        assert response.json()["email"] == new_driver.email

    @given("driver_id, status_code", [(28, status.HTTP_204_NO_CONTENT)])
    def test_get_driver_invalid_id(
        self, api_client: TestClient, driver_id: int, status_code: int
    ):
        """
        Should return no content when trying to get a driver with invalid or not existent id
        """
        response = api_client.get(f"{DRIVERS_PATH}{driver_id}")
        assert response.status_code == status_code

    @given("status_code", (status.HTTP_204_NO_CONTENT,))
    def test_no_drivers_in_database(
        self, auth_super_user, api_client: TestClient, status_code: status
    ):
        """
        Should return no content when there are no drivers in the database
        """
        response = api_client.get(DRIVERS_PATH)

        for item in response.json():
            driver_id = item["id"]
            api_client.delete(
                f"{DRIVERS_PATH}{driver_id}", headers=auth_super_user
            )

        response = api_client.get(DRIVERS_PATH)
        assert response.status_code == status_code

    @given(
        "first_name, last_name, ssn, email, status_code",
        [
            (
                "Fake_Driver_FN",
                "Fake_Drive_LN",
                "Fake_SNN",
                "fakemail@mail.com",
                status.HTTP_201_CREATED,
            )
        ],
    )
    def test_create_new_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        first_name: str,
        last_name: str,
        ssn: str,
        email: EmailStr,
        status_code: int,
    ):
        """
        Should return created http status and the new object in response
        """
        new_driver = {
            "first_name": first_name,
            "last_name": last_name,
            "ssn": ssn,
            "email": email,
        }

        response = api_client.post(
            DRIVERS_PATH,
            data=json.dumps(obj=new_driver),
            headers=auth_super_user,
        )

        assert response.status_code == status_code
        assert response.json()["first_name"] == first_name
        assert response.json()["last_name"] == last_name
        assert response.json()["ssn"] == ssn
        assert response.json()["email"] == email

    @given(
        "first_name, last_name, ssn, email, status_code",
        [
            (
                "Fake_Driver_FN",
                "Fake_Drive_LN",
                "Fake_SNN",
                "fakemail@mail.com",
                status.HTTP_401_UNAUTHORIZED,
            )
        ],
    )
    def test_create_new_driver_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        first_name: str,
        last_name: str,
        ssn: str,
        email: EmailStr,
        status_code: int,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_driver = {
            "first_name": first_name,
            "last_name": last_name,
            "ssn": ssn,
            "email": email,
        }

        response = api_client.post(
            DRIVERS_PATH,
            data=json.dumps(obj=new_driver),
            headers=auth_user,
        )

        assert response.status_code == status_code

    @given("email", ("newfakemail@mail.com",))
    def test_update_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        email: EmailStr,
    ):
        """
        Should return OK http status and the modified object.
        """
        new_driver = create_new_driver
        update_driver_obj = {"email": email}

        response = api_client.put(
            f"{DRIVERS_PATH}{new_driver.id}",
            data=json.dumps(update_driver_obj),
            headers=auth_super_user,
        )

        assert response.status_code == status.HTTP_200_OK
        assert response.json()["id"] == new_driver.id
        assert response.json()["last_name"] == new_driver.last_name
        assert response.json()["ssn"] == new_driver.ssn
        assert response.json()["first_name"] == new_driver.first_name
        assert response.json()["email"] != new_driver.email

    @given(
        "email, status_code",
        [("newfakemail@mail.com", status.HTTP_401_UNAUTHORIZED)],
    )
    def test_update_driver_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_driver: Driver,
        email: EmailStr,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_driver = create_new_driver
        update_driver_obj = {"email": email}

        response = api_client.put(
            f"{DRIVERS_PATH}{new_driver.id}",
            data=json.dumps(update_driver_obj),
            headers=auth_user,
        )

        assert response.status_code == status_code

    @given("driver_id, email", [(1321, "fake.mail.update@test.com")])
    def test_update_non_existent_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        driver_id: int,
        email: EmailStr,
    ):
        """
        Should return an error with internal server error http status and the information about this.
        """
        new_driver = {"email": email}
        response = api_client.put(
            f"{DRIVERS_PATH}{driver_id}",
            data=json.dumps(new_driver),
            headers=auth_super_user,
        )

        assert response.status_code == status.HTTP_500_INTERNAL_SERVER_ERROR
        assert (
            response.json()["message"]
            == "The requested Driver updatable object does not exists"
        )

    @given(
        "delete_status_code, query_status_code",
        [(status.HTTP_200_OK, status.HTTP_204_NO_CONTENT)],
    )
    def test_remove_a_driver(
        self,
        auth_super_user,
        api_client: TestClient,
        create_new_driver: Driver,
        delete_status_code: status,
        query_status_code: status,
    ):
        """
        Should return OK http status and the removed object
        """
        new_driver = create_new_driver

        response = api_client.delete(
            f"{DRIVERS_PATH}{new_driver.id}", headers=auth_super_user
        )
        assert response.status_code == delete_status_code

        was_removed = api_client.get(f"{DRIVERS_PATH}{new_driver.id}")
        assert was_removed.json()["message"] == ""
        assert was_removed.status_code == query_status_code

    @given(
        "status_code",
        (status.HTTP_401_UNAUTHORIZED,),
    )
    def test_remove_driver_as_non_super_user(
        self,
        auth_user,
        api_client: TestClient,
        create_new_driver: Driver,
        status_code: status,
    ):
        """
        Should raise an AuthException with Non Authorized http status code
        """
        new_driver = create_new_driver

        response = api_client.delete(
            f"{DRIVERS_PATH}{new_driver.id}", headers=auth_user
        )

        assert response.status_code == status_code

    @given(
        "driver_id, status_code, error_message, details_message",
        [
            (
                1100,
                status.HTTP_400_BAD_REQUEST,
                "The requested Driver object does not exists with this id",
                "The informed object with id 1100 probably does not exist in the database",
            )
        ],
    )
    def test_remove_driver_with_invalid_id(
        self,
        auth_super_user,
        api_client: TestClient,
        driver_id: int,
        status_code: status,
        error_message: str,
        details_message: str,
    ):
        """
        Should return an error with bad request http status and the information about this.
        """
        response = api_client.delete(
            f"{DRIVERS_PATH}{driver_id}", headers=auth_super_user
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message
        assert response.json()["details"] == details_message
        assert response.json()["error"] is True
