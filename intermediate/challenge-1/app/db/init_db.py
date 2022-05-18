from sqlalchemy.orm import Session

import crud
import schemas
from core.config import settings
from db import base  # noqa: F401


def init_db(db: Session) -> None:
    user = crud.user.get_by_email(db, email=settings.FIRST_MANAGER)
    if not user:
        user_in = schemas.UserCreate(
            email=settings.FIRST_MANAGER,
            password=settings.FIRST_MANAGER_PASSWORD,
            is_manager=True,
        )
        user = crud.user.create(db, obj_in=user_in)
