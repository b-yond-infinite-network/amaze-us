import random

import pytest
from fastapi import status
from pydantic import EmailStr

from src.exceptions.db_exception import DBException
from src.services.driver_service import driver
from src.database.db import ActiveSession
from src.schemas.driver_schema import DriverCreate, DriverUpdate, Driver

given = pytest.mark.parametrize


@pytest.mark.usefixtures()
class TestDriverService:
    """
    Test class responsible to execute unit test cases in driver service
    """

    @given("response_len", (0,))
    def test_get_all_drivers(
        self, db: ActiveSession, create_new_driver: Driver, response_len: int
    ):
        """
        Should be able to get all drivers in the database, limited to 100
        Should return a list with all drivers, limited to 100
        """
        drivers = driver.get_all(db_session=db)
        assert drivers is not None
        assert len(drivers) > response_len

    def test_get_driver_by_id(
        self, db: ActiveSession, create_new_driver: Driver
    ):
        """
        Should be able to get a driver given a valid id
        Should return the driver data
        """
        new_driver = create_new_driver
        the_driver = driver.get(db_session=db, id=new_driver.id)

        assert the_driver is not None
        assert the_driver.first_name == new_driver.first_name
        assert the_driver.last_name == new_driver.last_name

    @given(
        "driver_first_name, driver_last_name, driver_update_firstname, driver_update_lastname",
        [
            (
                "fake_firstname",
                "fake_lastname",
                "TEST DRIVER FIRSTNAME UPDATE",
                "TEST DRIVER LASTNAME UPDATE",
            )
        ],
    )
    def test_update_driver_given_an_id(
        self,
        db: ActiveSession,
        driver_first_name: str,
        driver_last_name: str,
        driver_update_firstname: str,
        driver_update_lastname: str,
    ):
        """
        Should not be able to update a driver given a valid id and data
        Should return the updated driver object
        """
        driver_obj = DriverCreate(
            first_name=driver_first_name,
            last_name=driver_last_name,
            ssn="fake_ssn",
            email="fakeemail@email.com",
        )
        created_driver = driver.create(db_session=db, input_object=driver_obj)

        updatable_driver_obj = DriverUpdate(
            first_name=driver_update_firstname,
            last_name=driver_update_lastname,
        )

        updated_driver = driver.update(
            db_session=db,
            db_obj=created_driver,
            input_object=updatable_driver_obj,
        )

        assert updated_driver.first_name != driver_first_name
        assert updated_driver.last_name != driver_last_name

    @given(
        "columns, message, status_code",
        [
            (
                ["ssn"],
                "Duplicate key for columns ['ssn']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_driver_with_an_existent_ssn(
        self,
        db: ActiveSession,
        columns: list,
        message: str,
        status_code: status,
    ):
        """
        Should not be able to create a driver if informed ssn belongs to another driver
        Should return a DBException for trying to insert a ssn that belongs to another driver
        """
        driver1_obj = DriverCreate(
            first_name="fake_firstname",
            last_name="fake_lastname",
            ssn=f"fake_ssn_12345_{random.randint(0, 2313123214)}",
            email=f"fake.ssn.test{random.randint(23213, 3129341)}@email.com",
        )
        driver1 = driver.create(db_session=db, input_object=driver1_obj)

        driver2_obj = DriverCreate(
            first_name="err_fake_firstname",
            last_name="err_fake_lastname",
            ssn=f"fake_ssn_1234567_{random.randint(0, 2313123214)}",
            email=f"fake.ssn.test{random.randint(23213, 3129341)}@mail.com",
        )
        driver2 = driver.create(db_session=db, input_object=driver2_obj)

        updatable_driver = DriverUpdate(ssn=driver1.ssn)

        with pytest.raises(DBException) as exc:
            driver.update(
                db_session=db, db_obj=driver2, input_object=updatable_driver
            )
        assert exc.value.error is True
        assert exc.value.columns == columns
        assert exc.value.status_code == status_code
        assert exc.value.message == message

    @given(
        "first_driver_id, second_driver_id, columns, error_message, status_code",
        [
            (
                1,
                2,
                ["email"],
                "Duplicate key for columns ['email']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_a_driver_with_an_existent_email(
        self,
        db: ActiveSession,
        first_driver_id: int,
        second_driver_id: int,
        columns: list,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to update a driver if informed email belongs to another driver
        Should return a DBException for trying to insert an email that belongs to another driver
        """
        the_email_driver = driver.get(db_session=db, id=first_driver_id)
        the_error_email_driver = driver.get(db_session=db, id=second_driver_id)

        updatable_driver = DriverUpdate(email=the_email_driver.email)

        with pytest.raises(DBException) as exc:
            driver.update(
                db_session=db,
                db_obj=the_error_email_driver,
                input_object=updatable_driver,
            )
        assert exc.value.error is True
        assert exc.value.columns == columns
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "ssn, email, first_name, last_name",
        [
            (
                "TEST-NEW-DRIVER-SSN-123",
                "test.newdriver@mail.com",
                "Test New Driver First Name",
                "Test New Driver Last Name",
            )
        ],
    )
    def test_create_new_driver(
        self,
        db: ActiveSession,
        ssn: str,
        email: EmailStr,
        first_name: str,
        last_name: str,
    ):
        """
        Should be able to create a new driver
        """
        new_driver = DriverCreate(
            ssn=ssn, email=email, first_name=first_name, last_name=last_name
        )

        created_driver = driver.create(db_session=db, input_object=new_driver)

        assert created_driver.ssn == ssn
        assert created_driver.email == email
        assert created_driver.first_name == first_name
        assert created_driver.last_name == last_name

    @given(
        "ssn, email, first_name, last_name, columns, error_message, status_code",
        [
            (
                "TEST-SSN-1234",
                "duplicated.email@mail.com",
                "Test Duplicated EMail Driver First Name",
                "Test Duplicated EMail Driver Last Name",
                ["email"],
                "Duplicate key for columns ['email']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_new_driver_with_an_existent_email(
        self,
        db: ActiveSession,
        ssn: str,
        email: EmailStr,
        first_name: str,
        last_name: str,
        columns: list,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a driver if informed email belongs to another driver
        Should return a DBException for trying to insert an email that belongs to another driver
        """
        new_driver = DriverCreate(
            ssn=ssn, email=email, first_name=first_name, last_name=last_name
        )

        driver.create(db_session=db, input_object=new_driver)
        error_ssn_driver = DriverCreate(
            ssn="SSN test for email error",
            email=email,
            first_name="Error Email Firstname Test",
            last_name="Error Email Lastname Test",
        )

        with pytest.raises(DBException) as exc:
            driver.create(db_session=db, input_object=error_ssn_driver)

        assert exc.value.error is True
        assert exc.value.columns == columns
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given(
        "columns, error_message, status_code",
        [
            (
                ["ssn"],
                "Duplicate key for columns ['ssn']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_driver_with_an_existent_ssn(
        self,
        db: ActiveSession,
        create_new_driver: Driver,
        columns: list,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a driver if informed ssn belongs to another driver
        Should return a DBException for trying to insert a ssn that belongs to another driver
        """
        new_driver = create_new_driver

        error_ssn_driver = DriverCreate(
            ssn=new_driver.ssn,
            email="error_fake_ssn_create@test.com",
            first_name="Error SSN Firstname Test",
            last_name="Error SSN Lastname Test",
        )

        with pytest.raises(DBException) as exc:
            driver.create(db_session=db, input_object=error_ssn_driver)

        assert exc.value.error is True
        assert exc.value.columns == columns
        assert exc.value.status_code == status_code
        assert exc.value.message == error_message
