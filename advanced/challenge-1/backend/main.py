from base64 import b64encode
import logging

import coloredlogs
from fastapi import FastAPI, HTTPException, Depends
from passlib.crypto.digest import pbkdf2_hmac
from sqlalchemy.orm import Session

from app.db import schemas
from app.db.crud import get_user_by_email, initialize_db
from app.db.database import Base, engine, get_conn, get_db


logging.config.fileConfig('logging.conf', disable_existing_loggers=False)
coloredlogs.install(
    fmt='%(asctime)s,%(msecs)03d %(levelname)-8s %(name)s:l%(lineno)-4d %(message)s')

Base.metadata.create_all(bind=engine)
# I chose not to use alembic as I would not need any more migrations
initialize_db(get_conn())

app = FastAPI()


@app.post('/authenticate/')
def authenticate(user: schemas.UserCreate, db: Session = Depends(get_db)):
    found = get_user_by_email(db, user.email)
    if not found:
        raise HTTPException(status_code=400, detail='User not found')

    input_hash = pbkdf2_hmac(
        'sha256', b64encode(user.password.encode()), found.salt, 100_000, 256)

    if input_hash == found.hash:
        return True
    raise HTTPException(status_code=400, detail='Wrong password')
