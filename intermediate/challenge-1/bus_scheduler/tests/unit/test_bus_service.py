import pytest
from fastapi import status

from src.database.db import ActiveSession
from src.exceptions.app_exception import AppException
from src.exceptions.db_exception import DBException
from src.services.bus_service import bus
from src.schemas.bus_schema import BusCreate, BusUpdate

given = pytest.mark.parametrize


@pytest.mark.usefixtures()
class TestBusService:
    """
    Test class responsible to execute unit test cases in bus endpoints
    """

    @given("response_len", (0,))
    def test_get_all_buses(self, db: ActiveSession, response_len: int):
        """
        Should be able to get all buses in the database, limited to 100
        Should return a list of all buses
        """
        buses = bus.get_all(db_session=db)
        assert buses is not None
        assert len(buses) > response_len

    @given("bus_id", [1, 2])
    def test_get_bus_by_id(self, db: ActiveSession, bus_id: int):
        """
        Should be able to return a bus given a valid id
        """
        the_bus = bus.get(db_session=db, id=bus_id)
        assert the_bus is not None

    @given("model, make, capacity", [("Test Bus Model", "Test Bus Make", 25)])
    def test_update_bus_given_an_id(
        self, db: ActiveSession, model: str, make: str, capacity: int
    ):
        """
        Should be able to update a bus given a valid id
        Should return the updated bus object in response
        """
        bus_obj = BusCreate(
            model="MODEL TO UPDATE", make="MAKE TO UPDATE", capacity=50
        )

        new_bus = bus.create(db_session=db, input_object=bus_obj)

        updatable_bus = BusUpdate()
        updatable_bus.model = model
        updatable_bus.make = make
        updatable_bus.capacity = capacity

        updated_bus = bus.update(
            db_session=db, db_obj=new_bus, input_object=updatable_bus
        )

        assert updated_bus.model == model
        assert updated_bus.make == make
        assert updated_bus.capacity == capacity

    @given(
        "bus_id, fake_model, error_message, error_details, status_code",
        [
            (
                109092,
                "Model FAKE",
                "The requested Bus updatable object does not exists",
                "The given id probably does not exist in the database",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_bus_with_non_existent_id(
        self,
        db: ActiveSession,
        bus_id: int,
        fake_model: str,
        error_message: str,
        error_details: str,
        status_code: status,
    ):
        """
        Should not be able to update a bus with an invalid/not existent id
        Should raise an AppException
        """
        the_invalid_bus = bus.get(db_session=db, id=bus_id)

        updatable_bus = BusUpdate()
        updatable_bus.model = fake_model

        with pytest.raises(AppException) as exc:
            bus.update(
                db_session=db,
                db_obj=the_invalid_bus,
                input_object=updatable_bus,
            )
        assert exc.value.error is True
        assert exc.value.message == error_message
        assert exc.value.details == error_details
        assert exc.value.status_code == status_code

    @given(
        "bus_id, fake_driver_id, error_message, status_code, is_foreign_key_error",
        [
            (
                1,
                12304321,
                "An error occurred while trying to save the entity's foreign key",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
                True,
            )
        ],
    )
    def test_update_bus_with_non_existent_driver_id(
        self,
        db: ActiveSession,
        bus_id: int,
        fake_driver_id: int,
        error_message: str,
        status_code: status,
        is_foreign_key_error: bool,
    ):
        """
        Should not be able to update a bus giving an invalid/non-existent driver id in object
        Should raise a DBException with internal server error status code
        """
        the_bus = bus.get(db_session=db, id=bus_id)

        updatable_bus = BusUpdate()
        updatable_bus.driver_id = fake_driver_id

        with pytest.raises(DBException) as exc:
            bus.update(
                db_session=db, db_obj=the_bus, input_object=updatable_bus
            )
        assert exc.value.error is True
        assert exc.value.fk_error is is_foreign_key_error
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "capacity, model, make", [(15, "FAKE TEST MODEL", "FAKE TEST MAKE")]
    )
    def test_create_new_bus(
        self, db: ActiveSession, capacity: int, model: str, make: str
    ):
        """
        Should be able to create a new bus
        Should return the new created bus object
        """
        new_bus = BusCreate(model=model, make=make, capacity=capacity)

        created_bus = bus.create(db_session=db, input_object=new_bus)

        assert created_bus.model == model
        assert created_bus.make == make
        assert created_bus.capacity == capacity
        assert created_bus.driver_id is None

    @given(
        "capacity, model, make, columns, error_message, status_code",
        [
            (
                15,
                "FAKE TEST MODEL",
                "FAKE TEST MAKE",
                ["capacity", "model", "make"],
                "Duplicate key for columns ['capacity', 'model', 'make']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_the_same_bus_twice(
        self,
        db: ActiveSession,
        capacity: str,
        model: str,
        make: str,
        columns: list,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create the same bus twice
        Should return a DBException with internal server error status code
        """
        new_bus = BusCreate(model=model, make=make, capacity=capacity)

        with pytest.raises(DBException) as exc:
            bus.create(db_session=db, input_object=new_bus)

        assert exc.value.error is True
        assert exc.value.columns == columns
        assert exc.value.message == error_message
        assert exc.value.status_code == status_code

    @given(
        "capacity, model, make, driver_id",
        [(15, "FAKE TEST MODEL WITH DRIVER", "FAKE TEST MAKE WITH DRIVER", 1)],
    )
    def test_create_bus_with_valid_driver_id(
        self,
        db: ActiveSession,
        capacity: str,
        model: str,
        make: str,
        driver_id: int,
    ):
        """
        Should be able to create a new bus with a valid/existent driver_id
        Should return the new created bus object
        """
        new_bus = BusCreate(
            model=model, make=make, capacity=capacity, driver_id=driver_id
        )

        created_bus = bus.create(db_session=db, input_object=new_bus)

        assert created_bus.model == model
        assert created_bus.make == make
        assert created_bus.capacity == capacity
        assert created_bus.driver_id == driver_id

    @given(
        "capacity, model, make, driver_id, error_message, status_code",
        [
            (
                15,
                "FAKE TEST MODEL WITH DRIVER",
                "FAKE TEST MAKE WITH DRIVER",
                1,
                "Duplicate key for columns",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_the_same_bus_with_driver_id_twice(
        self,
        db: ActiveSession,
        capacity: str,
        model: str,
        make: str,
        driver_id: int,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create the same bus with the same driver twice
        Should raise a DBException with internal server error status code
        """
        new_bus = BusCreate(
            model=model, make=make, capacity=capacity, driver_id=driver_id
        )

        with pytest.raises(DBException) as exc:
            bus.create(db_session=db, input_object=new_bus)

        assert exc.value.error is True
        assert exc.value.status_code == status_code
        assert error_message in exc.value.message

    @given(
        "capacity, model, make, driver_id, error_message, status_code, is_foreign_key_error",
        [
            (
                20,
                "NEW FAKE TEST MODEL",
                "NEW FAKE TEST MAKE",
                12314,
                "An error occurred while trying to save the entity's foreign key",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
                True,
            )
        ],
    )
    def test_create_new_bus_with_non_existent_driver_id(
        self,
        db: ActiveSession,
        capacity: str,
        model: str,
        make: str,
        driver_id: int,
        error_message: str,
        status_code: status,
        is_foreign_key_error: bool,
    ):
        """
        Should not be able to create a bus with an invalid/not-existent driver id
        Should raise DBException with internal server error status code
        """
        new_bus = BusCreate(
            model=model, make=make, capacity=capacity, driver_id=driver_id
        )

        with pytest.raises(DBException) as exc:
            bus.create(db_session=db, input_object=new_bus)

        assert exc.value.error is True
        assert exc.value.fk_error is is_foreign_key_error
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given("bus_id", (1,))
    def test_remove_by_id(self, db: ActiveSession, bus_id: int):
        """
        Should be able to remove a bus given a valid id
        Should return the removed object from database
        """
        removed_bus = bus.remove(db_session=db, id=bus_id)
        has_removed = bus.get(db_session=db, id=removed_bus.id)

        assert removed_bus.id == bus_id
        assert has_removed is None

    @given(
        "bus_id, error_message, error_details, status_code",
        [
            (
                13213,
                "The requested Bus object does not exists with this id",
                "The informed object with id 13213 probably does not exist in the database",
                status.HTTP_400_BAD_REQUEST,
            ),
        ],
    )
    def test_remove_by_invalid_id(
        self,
        db: ActiveSession,
        bus_id: int,
        error_message: str,
        error_details: str,
        status_code: status,
    ):
        """
        Should not be able to remove a bus given an invalid id
        Should raise an AppException with bad request status code
        """
        with pytest.raises(AppException) as exc:
            bus.remove(db_session=db, id=bus_id)

        assert exc.value.message == error_message
        assert exc.value.details == error_details
        assert exc.value.status_code == status_code
