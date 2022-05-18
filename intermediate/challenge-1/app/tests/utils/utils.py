import random
import string
from typing import Dict

from fastapi.testclient import TestClient

from core.config import settings


def random_lower_string() -> str:
    return "".join(random.choices(string.ascii_lowercase, k=32))


def random_ssn():
    return "".join(random.choices(string.digits, k=9))


def random_email() -> str:
    return f"{random_lower_string()}@{random_lower_string()}.com"


def get_manager_token_headers(client: TestClient) -> Dict[str, str]:
    login_data = {
        "username": settings.FIRST_MANAGER,
        "password": settings.FIRST_MANAGER_PASSWORD,
    }
    r = client.post("login/access-token", data=login_data)
    tokens = r.json()
    a_token = tokens["access_token"]
    headers = {"Authorization": f"Bearer {a_token}"}
    return headers
