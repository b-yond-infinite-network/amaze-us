import json

from app.repositories.BusRepository import BusRepository


def test_create_bus(get_app, monkeypatch):
    dummy_data = {"id": 1, "capacity": 30, "model": "VW", "maker": "VW", "driver_id": 0}

    def mock_post(payload, session):
        return dummy_data

    monkeypatch.setattr(target=BusRepository, name="post", value=mock_post)

    response = get_app.post("/bus/", data=json.dumps({"capacity": 30, "model": "VW", "maker": "VW", "driver_id": 0}))
    assert response.status_code == 201
    assert response.json() == dummy_data


def test_create_bus_wrong_payload(get_app, monkeypatch):
    response = get_app.post("/bus/", data=json.dumps({"capacity": 10}))
    assert response.status_code == 422


def test_get_bus(get_app, monkeypatch):
    dummy_data = {"id": 1, "capacity": 30, "model": "VW", "maker": "VW", "driver_id": 0}

    def mock_get(id, session):
        return dummy_data

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)

    response = get_app.get("/bus/1")
    assert response.status_code == 200
    assert response.json() == dummy_data


def test_get_bus_wrong_id(get_app, monkeypatch):
    def mock_get(id, session):
        return None

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)

    response = get_app.get("/bus/2")
    assert response.status_code == 404
    assert response.json()["detail"] == "Bus not found."


def test_update_bus(get_app, monkeypatch):
    dummy_data = {"id": 1, "capacity": 30, "model": "VW", "maker": "VW", "driver_id": 0}
    update_data = {"id": 1, "capacity": 150, "model": "Z300", "maker": "GM", "driver_id": 0}

    def mock_get(self, id):
        return dummy_data

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)

    def mock_update(self, bus_model, payload):
        return update_data

    monkeypatch.setattr(target=BusRepository, name="update", value=mock_update)

    response = get_app.put("/bus/1/", data=json.dumps(update_data))
    assert response.status_code == 200
    assert response.json() == update_data


def test_update_bus_w_wrong_id(get_app, monkeypatch):
    update_data = {"id": 1, "capacity": 150, "model": "Z300", "maker": "GM", "driver_id": 0}

    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)

    response = get_app.put("/bus/1000/", data=json.dumps(update_data))
    assert response.status_code == 404


def test_delete_bus(get_app, monkeypatch):
    dummy_data = {"id": 1, "capacity": 150, "model": "Z300", "maker": "GM", "driver_id": 0}

    def mock_get(self, id):
        return dummy_data

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)

    def mock_delete(self, bus_model):
        return None

    monkeypatch.setattr(target=BusRepository, name="delete", value=mock_delete)

    response = get_app.delete("/bus/1/")
    assert response.status_code == 200


def test_delete_bus_wrong_id(get_app, monkeypatch):
    def mock_get(self, id):
        return None

    monkeypatch.setattr(target=BusRepository, name="get", value=mock_get)
    response = get_app.delete("/bus/2/")
    assert response.status_code == 404

