import pytest
from pydantic import EmailStr
from fastapi import status

from src.database.db import ActiveSession
from src.exceptions.db_exception import DBException
from src.schemas.user_schema import UserCreate, UserUpdate
from src.models.user_model import User
from src.services.user_service import user

given = pytest.mark.parametrize


@pytest.mark.usefixtures()
class TestUserService:
    """
    Test class responsible to execute unit test cases in user endpoints
    """

    def test_get_user_by_email(self, db: ActiveSession, create_user: User):
        fake_user = create_user
        the_user = user.get_by_email(db_session=db, email=fake_user.email)

        assert the_user.first_name == fake_user.first_name
        assert the_user.id == fake_user.id

    @given(
        "first_name, last_name, email, is_superuser, password",
        [
            (
                "Fake-user-first-name",
                "fake_user_last_name",
                "fake_user@mail.com",
                True,
                "123456",
            )
        ],
    )
    def test_create_user(
        self,
        db: ActiveSession,
        first_name: str,
        last_name: str,
        email: EmailStr,
        is_superuser: bool,
        password: str,
    ):
        """
        Should be able to create a new user
        """
        new_user = UserCreate(
            first_name=first_name,
            last_name=last_name,
            email=email,
            is_superuser=is_superuser,
            password=password,
        )

        created_user = user.create(db_session=db, input_object=new_user)

        assert created_user.first_name == first_name
        assert created_user.last_name == last_name
        assert created_user.email == email
        assert created_user.is_superuser == is_superuser

    @given(
        "first_name, last_name, is_superuser, password, error_message, status_code",
        [
            (
                "Fake-first-user",
                "fake_last_name",
                True,
                "123456",
                "Duplicate key for columns ['email']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_create_invalid_user(
        self,
        db: ActiveSession,
        create_user: User,
        first_name: str,
        last_name: str,
        is_superuser: bool,
        password: str,
        error_message: str,
        status_code: status,
    ):
        """
        Should not be able to create a user with an existent db email
        """
        new_user = create_user
        dupe_user = UserCreate(
            first_name=first_name,
            last_name=last_name,
            is_superuser=is_superuser,
            password=password,
            email=new_user.email,
        )

        with pytest.raises(DBException) as exc:
            user.create(db_session=db, input_object=dupe_user)

        assert exc.value.status_code == status_code
        assert exc.value.message == error_message

    @given("password", ("fake-passwd-12345678",))
    def test_update_user_pass(
        self, db: ActiveSession, create_user: User, password: str
    ):
        new_user = create_user
        old_pass = new_user.hashed_password

        update_user_obj = UserUpdate(password=password)

        updated_user = user.update(
            db_session=db, db_obj=new_user, input_object=update_user_obj
        )
        assert updated_user.hashed_password != old_pass

    @given(
        "error_message, status_code",
        [
            (
                "Duplicate key for columns ['email']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_user_email(
        self,
        db: ActiveSession,
        create_user: User,
        error_message: str,
        status_code: status,
    ):
        new_user = create_user
        error_user_obj = UserCreate(
            first_name="Fake_User_Test_Email_UPDATE",
            last_name="Fake_User_Test_Email_UPDATE",
            is_superuser=False,
            password="123455",
            email="fake.test.user.update@updatemail.com",
        )

        error_user_db = user.create(db_session=db, input_object=error_user_obj)
        error_update_user_obj = UserUpdate(email=new_user.email)

        with pytest.raises(DBException) as exc:
            user.update(
                db_session=db,
                db_obj=error_user_db,
                input_object=error_update_user_obj,
            )

        assert exc.value.message == error_message
        assert exc.value.status_code == status_code

    @given(
        "error_message, status_code",
        [
            (
                "Duplicate key for columns ['email']",
                status.HTTP_500_INTERNAL_SERVER_ERROR,
            )
        ],
    )
    def test_update_user_email(
        self,
        db: ActiveSession,
        create_user: User,
        error_message: str,
        status_code: status,
    ):
        new_user = create_user
        error_user_obj = UserCreate(
            first_name="Fake_User_Test_Email_UPDATE_2",
            last_name="Fake_User_Test_Email_UPDATE_2",
            is_superuser=False,
            password="123455",
            email="fake.test.user.update2@updatemail.com",
        )

        error_user_db = user.create(db_session=db, input_object=error_user_obj)
        error_update_user_obj = {"email": new_user.email}

        with pytest.raises(DBException) as exc:
            user.update(
                db_session=db,
                db_obj=error_user_db,
                input_object=error_update_user_obj,
            )

        assert exc.value.message == error_message
        assert exc.value.status_code == status_code

    def test_user_is_superuser(self, db: ActiveSession, create_user: User):
        new_user = create_user
        is_super_user = user.is_superuser(new_user)

        assert is_super_user is new_user.is_superuser
