import pytest
from starlette.testclient import TestClient

from app.main import app


@pytest.fixture(scope="module")
def get_app():
    client = TestClient(app)
    yield client


@pytest.fixture(scope="session")
def dummy_payload():
    return {"id": 1, "capacity": 30, "model": "VW", "maker": "VW", "driver_id": 0}
