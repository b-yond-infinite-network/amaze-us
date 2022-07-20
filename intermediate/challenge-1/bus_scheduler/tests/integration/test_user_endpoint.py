import json

import pytest
from fastapi import status

given = pytest.mark.parametrize
USERS_PATH = "/api/v1/users"


class TestUserEndpoint:
    """
    Test class responsible to execute integration test cases in user endpoints
    """

    @given("username, status_code", [("admin", status.HTTP_200_OK)])
    def test_security_oauth2(
        self, api_client, auth_super_user, username: str, status_code: status
    ):
        response = api_client.get(f"{USERS_PATH}/me", headers=auth_super_user)
        assert response.status_code == status_code, response.text
        assert response.json()["first_name"] == username

    @given("username, status_code", [("user", status.HTTP_200_OK)])
    def test_security_oauth2_password_other_header(
        self, api_client, auth_user, username: str, status_code: status
    ):
        response = api_client.get(f"{USERS_PATH}/me", headers=auth_user)
        assert response.status_code == status_code, response.text
        assert response.json()["first_name"] == username

    @given(
        "message, status_code",
        [("Not authenticated", status.HTTP_401_UNAUTHORIZED)],
    )
    def test_security_oauth2_password_bearer_no_header(
        self, api_client, message: str, status_code: status
    ):
        response = api_client.get(f"{USERS_PATH}/me")
        assert response.status_code == status_code, response.text
        assert response.json() == {"detail": message}

    @given(
        "data,expected_status",
        [
            (None, status.HTTP_422_UNPROCESSABLE_ENTITY),
            (
                {"username": "johndoe", "password": "secret"},
                status.HTTP_400_BAD_REQUEST,
            ),
            (
                {
                    "username": "johndoe",
                    "password": "secret",
                    "grant_type": "incorrect",
                },
                status.HTTP_422_UNPROCESSABLE_ENTITY,
            ),
            (
                {
                    "username": "admin@admin.com",
                    "password": "error-pass",
                    "grant_type": "password",
                },
                status.HTTP_400_BAD_REQUEST,
            ),
            (
                {
                    "username": "admin@admin.com",
                    "password": "123456",
                    "grant_type": "password",
                },
                status.HTTP_200_OK,
            ),
        ],
    )
    def test_strict_login(self, api_client, data, expected_status):
        response = api_client.post(f"{USERS_PATH}/login", data=data)
        assert response.status_code == expected_status

    @given(
        "data,expected_status",
        [
            (None, status.HTTP_422_UNPROCESSABLE_ENTITY),
            (
                {
                    "first_name": "fake_first_name",
                    "is_superuser": False,
                    "password": "fake_pass",
                },
                status.HTTP_422_UNPROCESSABLE_ENTITY,
            ),
            (
                {
                    "first_name": "fake_first_name",
                    "last_name": "fake_last_name",
                    "email": "fake-error-mail",
                    "is_superuser": True,
                    "password": "fake-pass",
                },
                status.HTTP_422_UNPROCESSABLE_ENTITY,
            ),
            (
                {
                    "first_name": "fake first name",
                    "last_name": "fake last name",
                    "email": "fake@mail.com",
                    "is_superuser": True,
                    "password": "fake-pass",
                },
                status.HTTP_201_CREATED,
            ),
            (
                {
                    "first_name": "fake first name",
                    "last_name": "fake last name",
                    "email": "fake@mail.com",
                    "is_superuser": True,
                    "password": "fake-pass",
                },
                status.HTTP_409_CONFLICT,
            ),
        ],
    )
    def test_create_user(self, api_client, data, expected_status):
        response = api_client.post(
            f"{USERS_PATH}/signup", data=json.dumps(data)
        )
        assert response.status_code == expected_status

    @given(
        "password, status_code",
        [("fake-pass-update-test-123456", status.HTTP_200_OK)],
    )
    def test_update_user(
        self, api_client, auth_user, password: str, status_code: status
    ):
        data = {"password": password}
        response = api_client.put(
            f"{USERS_PATH}/update", data=json.dumps(data), headers=auth_user
        )
        assert response.status_code == status_code

    @given(
        "status_code, error_message",
        [
            (
                status.HTTP_401_UNAUTHORIZED,
                "You can't set yourself as a superuser",
            )
        ],
    )
    def test_update_non_super_user(
        self, api_client, status_code: status, error_message: str
    ):
        """
        Should not be able to accept a non superuser set yourself as a superuser
        """
        user_data = {
            "first_name": "John",
            "last_name": "TestDoe",
            "email": "john.test.doe@mail.com",
            "is_superuser": False,
            "password": "123456788",
        }

        api_client.post(f"{USERS_PATH}/signup", data=json.dumps(user_data))

        login_data = {
            "username": user_data["email"],
            "password": user_data["password"],
        }
        token = api_client.post(f"{USERS_PATH}/login", data=login_data).json()

        update_data = {"is_superuser": True}

        response = api_client.put(
            f"{USERS_PATH}/update",
            data=json.dumps(update_data),
            headers={
                "Authorization": f"{token['token_type'].capitalize()} {token['access_token']}"
            },
        )

        assert response.status_code == status_code
        assert response.json()["message"] == error_message
