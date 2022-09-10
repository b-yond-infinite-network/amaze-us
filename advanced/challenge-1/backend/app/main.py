from random import randbytes
from typing import List

from fastapi import FastAPI, HTTPException
from passlib.crypto.digest import pbkdf2_hmac
from sqlalchemy.orm import Session

from .db import schemas
from .db.crud import get_user_by_email
from .db.database import Base, engine, SessionLocal
from .db.models import initialize_db


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

def get_conn():
    return next(get_db())


Base.metadata.create_all(bind=engine)
# I chose not to use alembic as I would not need any more migrations
initialize_db(get_conn())

app = FastAPI()


@app.post('/authenticate/')
def authenticate(user: schemas.UserCreate):
    found = get_user_by_email(get_conn(), user.email)
    if not found:
        raise HTTPException(status_code=400, detail='User not found')

    input_hash = pbkdf2_hmac('sha256', str.encode(user.password.encode()), found.salt, 100_000, 256)

    if str.encode(input_hash) == found.hash:
        return True
    raise HTTPException(status_code=400, detail='Wrong password')
