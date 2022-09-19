from datetime import timedelta
import logging

import coloredlogs
from fastapi import Depends, FastAPI, HTTPException, status
from fastapi.security import OAuth2PasswordRequestForm
from sqlalchemy.orm import Session

from app.auth.authentication import (
    authenticate_user,
    create_access_token,
    get_current_active_user
)
from app.db import models, schemas
from app.db.crud import initialize_db
from app.db.database import Base, engine, get_conn, get_db
from app.utils import settings


logging.config.fileConfig('logging.conf', disable_existing_loggers=False)
coloredlogs.install(
    fmt='%(asctime)s,%(msecs)03d %(levelname)-8s %(name)s:l%(lineno)-4d %(message)s')

Base.metadata.create_all(bind=engine)
# I chose not to use alembic as I would not need any more migrations
initialize_db(get_conn())

app = FastAPI()


@app.post("/token", response_model=schemas.Token)
async def login_for_access_token(
    form_data: OAuth2PasswordRequestForm = Depends(),
    db: Session = Depends(get_db)
):
    user = authenticate_user(db, form_data.username, form_data.password)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect username or password",
            headers={"WWW-Authenticate": "Bearer"},
        )
    access_token_expires = timedelta(
        minutes=settings.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = create_access_token(
        data={
            "sub": user.username,
            "scopes": user.scope
        }, expires_delta=access_token_expires)
    return {"access_token": access_token, "token_type": "bearer"}


@app.get("/users/me/", response_model=schemas.User)
async def read_users_me(
    current_user: models.User = Depends(get_current_active_user)
):
    return current_user
