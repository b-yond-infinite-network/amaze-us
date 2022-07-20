from typing import Any

from fastapi import APIRouter, BackgroundTasks, Depends, status
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm.session import Session

from src.core.email import send_registration_email
from src.context.user_context import CurrentUser
from src.core.auth import authenticate, create_access_token
from src.database.db import ActiveSession
from src.exceptions.app_exception import AppException
from src.exceptions.auth_exception import AuthException
from src.schemas.user_schema import User, UserCreate, UserUpdate
from src.services.user_service import user as user_service

router = APIRouter(prefix="/users", tags=["User"])


@router.post("/login")
def login(
    db: Session = ActiveSession,
    form_data: OAuth2PasswordRequestForm = Depends(),
) -> Any:
    """
    Get the JWT for a user with data from OAuth2 request form body.
    """

    user = authenticate(
        email=form_data.username, password=form_data.password, db=db
    )
    if not user:
        raise AppException(message="Incorrect username or password")

    return {
        "access_token": create_access_token(sub=user.id),
        "token_type": "bearer",
    }


@router.get("/me", response_model=User)
def read_users_me(current_user: User = CurrentUser):
    """
    Fetch the current logged-in user.
    """
    user = current_user
    return user


@router.post("/signup", response_model=User, status_code=201)
async def create_user_signup(
    *,
    user_in: UserCreate,
    background_task: BackgroundTasks,
    db: Session = ActiveSession,
) -> Any:
    """
    Create new user without the need to be logged in.
    """
    user = user_service.get_by_email(db_session=db, email=user_in.email)
    if user:
        raise AppException(
            status_code=status.HTTP_409_CONFLICT,
            message="The user with this email already exists in the system",
        )

    new_user = user_service.create(db_session=db, input_object=user_in)
    background_task.add_task(
        send_registration_email, user=new_user
    )

    return new_user


@router.put("/update", response_model=User)
def create_user_signup(
    *,
    user_in: UserUpdate,
    db: Session = ActiveSession,
    current_user: User = CurrentUser,
) -> Any:
    """
    Update a user
    """
    if user_in.is_superuser and current_user.is_superuser is False:
        raise AuthException(message="You can't set yourself as a superuser")

    return user_service.update(
        db_session=db, db_obj=current_user, input_object=user_in
    )
