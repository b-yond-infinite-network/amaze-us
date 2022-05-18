from typing import Dict
from fastapi.testclient import TestClient
from sqlalchemy.orm import Session
import crud
from schemas.user import UserCreate
from tests.utils.utils import random_email, random_lower_string



def test_get_existing_user(
        client: TestClient, manager_token_headers: dict, db: Session
) -> None:
    username = random_email()
    password = random_lower_string()
    user_in = UserCreate(email=username, password=password)
    user = crud.user.create(db, obj_in=user_in)
    user_id = user.id
    r = client.get(
        f"user/{user_id}", headers=manager_token_headers,
    )
    assert 200 <= r.status_code < 300
    api_user = r.json()
    existing_user = crud.user.get_by_email(db, email=username)
    assert existing_user
    assert existing_user.email == api_user["email"]


def test_create_user_by_employee_user(
        client: TestClient, employee_user_token_headers: Dict[str, str]
) -> None:
    username = random_email()
    password = random_lower_string()
    data = {"email": username, "password": password}
    r = client.post(
        "user/", headers=employee_user_token_headers, json=data,
    )
    assert r.status_code == 400


def test_retrieve_users(
        client: TestClient, manager_token_headers: dict, db: Session
) -> None:
    username = random_email()
    password = random_lower_string()
    user_in = UserCreate(email=username, password=password)
    crud.user.create(db, obj_in=user_in)

    username2 = random_email()
    password2 = random_lower_string()
    user_in2 = UserCreate(email=username2, password=password2)
    crud.user.create(db, obj_in=user_in2)

    r = client.get("user/", headers=manager_token_headers)
    all_users = r.json()

    assert len(all_users) > 1

