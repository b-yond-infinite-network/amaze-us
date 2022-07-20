from fastapi import Depends, HTTPException, status
from jose import JWTError, jwt

from src.config import settings
from src.core.auth import oauth2_scheme
from src.database.db import ActiveSession
from src.models import User
from src.schemas.token_schema import Token


async def get_current_user(
    token: str = Depends(oauth2_scheme),
    db: ActiveSession = ActiveSession,
) -> User:
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(
            token,
            settings.security.JWT_SECRET,
            algorithms=[settings.security.ALGORITHM],
            options={"verify_aud": False},
        )
        username: str = payload.get("sub")
        if username is None:
            raise credentials_exception
        token_data = Token(username=username)
    except JWTError:
        raise credentials_exception

    user = db.query(User).filter(User.id == token_data.username).first()
    if user is None:
        raise credentials_exception
    return user


CurrentUser = Depends(get_current_user)
