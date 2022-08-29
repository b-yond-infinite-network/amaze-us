import json
from datetime import datetime, timedelta

from app.repositories.ScheduleRepository import ScheduleRepository
from app.schemas.Schedule import ScheduleDb
from app.services.ScheduleService import ScheduleService
from app.tests.conftest import DateTimeEncoder


def test_create_schedule(get_app, dummy_payload, dummy_db_schedule, dummy_db_schedule_list, monkeypatch):
    def mock_get_driver_and_bus(self, payload):
        driver = {"id": 1, "first_name": "Mitchel", "last_name": "Maverick",
                "social_security_number": 12345, "email": "meverick@topgun.com"}
        bus = {"id": 1, "capacity": 10, "model": "GTI", "maker": "VW", "driver_id": 1}
        return driver, bus
    monkeypatch.setattr(target=ScheduleService, name="get_driver_and_bus", value=mock_get_driver_and_bus)

    def mock_get_schedule_by_week(self, dt):
        return dummy_db_schedule_list
    monkeypatch.setattr(target=ScheduleService, name="get_schedule_by_week", value=mock_get_schedule_by_week)

    def mock_post(self, payload):
        return dummy_db_schedule

    monkeypatch.setattr(target=ScheduleRepository, name="post", value=mock_post)

    response = get_app.post("/schedule/", data=json.dumps(dummy_payload, cls=DateTimeEncoder))
    assert response.status_code == 201
    assert ScheduleDb(**response.json()) == dummy_db_schedule


def test_create_schedule_wrong_payload(get_app, monkeypatch):
    response = get_app.post("/schedule/", data=json.dumps({"shift": 10}))
    assert response.status_code == 422


def test_get_schedule(get_app, monkeypatch, dummy_db_schedule):
    def mock_get(self, id):
        return dummy_db_schedule

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)

    response = get_app.get("/schedule/1")
    assert response.status_code == 200
    assert ScheduleDb(**response.json()) == dummy_db_schedule


def test_get_schedule_wrong_id(get_app, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)

    response = get_app.get("/schedule/2")
    assert response.status_code == 404
    assert response.json()["detail"] == "Schedule not found."


def test_update_schedule(get_app, dummy_payload, dummy_db_schedule, monkeypatch):
    updated_schedule = ScheduleDb(
        id=1,
        bus_id=2,
        driver_id=2,
        start_dt=datetime.now(),
        end_dt=datetime.now() + timedelta(minutes=20)
    )

    def mock_get(self, id):
        return dummy_db_schedule

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)

    def mock_update(self, schedule_model, payload):
        return updated_schedule

    monkeypatch.setattr(target=ScheduleRepository, name="update", value=mock_update)

    response = get_app.put("/schedule/1/", data=json.dumps(dummy_payload, cls=DateTimeEncoder))
    assert response.status_code == 200
    assert ScheduleDb(**response.json()) == updated_schedule


def test_update_schedule_w_wrong_id(get_app, dummy_payload, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)

    response = get_app.put("/bus/1000/", data=json.dumps(dummy_payload, cls=DateTimeEncoder))
    assert response.status_code == 404


def test_delete_schedule(get_app, dummy_payload, monkeypatch):
    def mock_get(self, id):
        return dummy_payload

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)

    def mock_delete(self, schedule_model):
        return None

    monkeypatch.setattr(target=ScheduleRepository, name="delete", value=mock_delete)

    response = get_app.delete("/schedule/1/")
    assert response.status_code == 200


def test_delete_schedule_wrong_id(get_app, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=ScheduleRepository, name="get", value=mock_get)
    response = get_app.delete("/bus/2/")
    assert response.status_code == 404
