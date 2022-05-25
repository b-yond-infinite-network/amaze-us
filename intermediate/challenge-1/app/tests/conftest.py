from typing import Dict, Generator
import pytest
from fastapi.testclient import TestClient
from sqlalchemy.orm import Session

from db.session import SessionLocal
from main import app
from tests.utils.user import authentication_token_from_email
from tests.utils.utils import get_manager_token_headers, random_email


@pytest.fixture(scope="session")
def db() -> Generator:
    yield SessionLocal()


@pytest.fixture(scope="module")
def client() -> Generator:
    with TestClient(app) as c:
        yield c


@pytest.fixture(scope="module")
def manager_token_headers(client: TestClient) -> Dict[str, str]:
    return get_manager_token_headers(client)


@pytest.fixture(scope="module")
def employee_user_token_headers(client: TestClient, db: Session) -> Dict[str, str]:
    return authentication_token_from_email(
        client=client, email=random_email(), db=db
    )
