from fastapi.testclient import TestClient

from core.config import settings


def test_get_access_token(client: TestClient) -> None:
    login_data = {
        "username": settings.FIRST_MANAGER,
        "password": settings.FIRST_MANAGER_PASSWORD,
    }
    r = client.post("login/access-token", data=login_data)
    tokens = r.json()
    assert r.status_code == 200
    assert "access_token" in tokens
    assert tokens["access_token"]

