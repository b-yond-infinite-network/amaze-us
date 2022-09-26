from fastapi import FastAPI
from fastapi.security import OAuth2PasswordRequestForm
from fastapi.testclient import TestClient
from jose import jwt

from app.db.initialize import initialize_db
from app.db.database import Base, engine, get_conn
from app.router import router
from app.utils import settings


Base.metadata.create_all(bind=engine)
# I chose not to use alembic as I would not need any more migrations
initialize_db(get_conn())

app = FastAPI()
app.include_router(router)
client = TestClient(app)

MANAGER_AUTH_DATA = {
    'username': 'manager@amazeus.com',
    'password': 'manager',
    'scope': 'manager'
}
response = client.post('/api/token', data=MANAGER_AUTH_DATA)
body = response.json()
MANAGER_TOKEN = body['access_token']


def test_invalid_login():
    response = client.post('/api/token', data={
        'username': 'nonexisting@amazeus.com',
        'password': 'somethingelse',
        'scope': ''
    })
    assert response.status_code == 401
    body = response.json()
    assert 'access_token' not in body


def test_get_drivers():
    response = client.get('/api/driver', headers={
        'Authorization': f'Bearer {MANAGER_TOKEN}'
    })
    assert response.status_code == 200
    body = response.json()
    assert len(body) == 1000


def test_get_buses():
    response = client.get('/api/bus', headers={
        'Authorization': f'Bearer {MANAGER_TOKEN}'
    })
    assert response.status_code == 200
    body = response.json()
    assert len(body) == 250
