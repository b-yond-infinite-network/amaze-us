import json

from app.repositories.DriverRepository import DriverRepository


def test_create_driver(get_app, dummy_payload, monkeypatch):

    def mock_post(self, payload):
        return dummy_payload

    monkeypatch.setattr(target=DriverRepository, name="post", value=mock_post)

    response = get_app.post("/driver/", data=json.dumps({
        "first_name": "Beavis",
        "last_name": "Butthead",
        "social_security_number": "1232",
        "email": "beavis@email.com"
    }))
    assert response.status_code == 201
    assert response.json() == dummy_payload


def test_create_driver_wrong_payload(get_app, monkeypatch):
    response = get_app.post("/driver/", data=json.dumps({"age": 10}))
    assert response.status_code == 422


def test_get_driver(get_app, dummy_payload, monkeypatch):
    def mock_get(self, id):
        return dummy_payload

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)

    response = get_app.get("/driver/1")
    assert response.status_code == 200
    assert response.json() == dummy_payload


def test_get_driver_wrong_id(get_app, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)

    response = get_app.get("/driver/2")
    assert response.status_code == 404
    assert response.json()["detail"] == "Driver not found."


def test_update_driver(get_app, dummy_payload, monkeypatch):
    updated_driver = {
        "id": 1,
        "first_name": "Mitchell ",
        "last_name": "Maverick",
        "social_security_number": 666,
        "email": "topgun@maverick.com"
    }

    def mock_get(self, id):
        return dummy_payload

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)

    def mock_update(self, driver_model, payload):
        return updated_driver

    monkeypatch.setattr(target=DriverRepository, name="update", value=mock_update)

    response = get_app.put("/driver/1/", data=json.dumps(dummy_payload))
    assert response.status_code == 200
    assert response.json() == updated_driver


def test_update_driver_w_wrong_id(get_app, dummy_payload, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)

    response = get_app.put("/bus/1000/", data=json.dumps(dummy_payload))
    assert response.status_code == 404


def test_delete_driver(get_app, dummy_payload, monkeypatch):
    def mock_get(self, id):
        return dummy_payload

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)

    def mock_delete(self, driver_model):
        return None

    monkeypatch.setattr(target=DriverRepository, name="delete", value=mock_delete)

    response = get_app.delete("/driver/1/")
    assert response.status_code == 200


def test_delete_driver_wrong_id(get_app, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=DriverRepository, name="get", value=mock_get)
    response = get_app.delete("/bus/2/")
    assert response.status_code == 404

