import logging
from datetime import datetime, timedelta

from fastapi import Depends, HTTPException, Security, status
from fastapi.security import OAuth2PasswordBearer, SecurityScopes
from jose import JWTError, jwt
from passlib.context import CryptContext
from pydantic import ValidationError
from sqlalchemy.orm import Session

from app.db import models, schemas
from app.db.crud import get_user_by_email
from app.db.database import get_db
from app.utils import settings


logger = logging.getLogger(__name__)
pwd_context = CryptContext(schemes=['bcrypt'], deprecated='auto')
oauth2_scheme = OAuth2PasswordBearer(tokenUrl='token', scopes={
    'employee': 'Read-only access to drivers/buses schedules',
    'manager': 'Write access to buses, drivers and shifts'
})


def verify_password(
    plain_password: str,
    hashed_password: str
):
    result = pwd_context.verify(plain_password, hashed_password)
    input_hash = get_password_hash(plain_password)
    logger.debug(
        f'Checking hashes: {input_hash} == {hashed_password}? {result}')
    return result


def get_password_hash(password: str):
    return pwd_context.hash(password)


def authenticate_user(db: Session, email: str, password: str):
    user = get_user_by_email(db, email)

    if not user:
        return False
    if not verify_password(password, user.hash):
        return False

    return user


def create_access_token(data: dict, expires_delta: timedelta | None = None):
    to_encode = data.copy()
    if expires_delta:
        expire = datetime.utcnow() + expires_delta
    else:
        expire = datetime.utcnow() + timedelta(minutes=15)
    to_encode.update({'exp': expire})
    encoded_jwt = jwt.encode(
        to_encode,
        settings['SECRET_KEY'],
        algorithm=settings['ALGORITHM']
    )
    return encoded_jwt


async def get_current_user(
    security_scopes: SecurityScopes,
    db: Session = Depends(get_db),
    token: str = Depends(oauth2_scheme)
):
    if security_scopes.scopes:
        authenticate_value = f'Bearer scope="{security_scopes.scope_str}"'
    else:
        authenticate_value = f'Bearer'

    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail='Could not validate credentials',
        headers={'WWW-Authenticate': authenticate_value},
    )

    try:
        logger.info(f'Verifying token: {token}')
        payload = jwt.decode(
            token,
            settings['SECRET_KEY'],
            algorithms=[settings['ALGORITHM']]
        )
        logger.info(f'Decoded payload: {payload}')

        username: str = payload.get('sub')
        if username is None:
            raise credentials_exception
    except (JWTError, ValidationError):
        raise credentials_exception

    user = get_user_by_email(db, username)
    if user is None:
        raise credentials_exception

    found = False
    for scope in security_scopes.scopes:
        if scope == user.scope:
            found = True
            break

    if not found:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail='Not enough permissions',
            headers={'WWW-Authenticate': authenticate_value},
        )
    return user
