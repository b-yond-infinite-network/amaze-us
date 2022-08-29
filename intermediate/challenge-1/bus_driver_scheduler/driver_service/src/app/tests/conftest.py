import pytest
from starlette.testclient import TestClient

from app.main import app


@pytest.fixture(scope="module")
def get_app():
    client = TestClient(app)
    yield client


@pytest.fixture(scope="session")
def dummy_payload():
    return {
        "id": 1,
        "first_name": "Beavis",
        "last_name": "Butthead",
        "social_security_number": 1232,
        "email": "beavis@email.com"
    }
