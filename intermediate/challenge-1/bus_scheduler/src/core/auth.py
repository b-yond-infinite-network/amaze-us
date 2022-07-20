from datetime import datetime, timedelta
from typing import List, MutableMapping, Optional, Union

from fastapi.security import OAuth2PasswordBearer
from jose import jwt

from src.config import settings
from src.core.security import verify_password
from src.database.db import ActiveSession
from src.models import User

JWTPayloadMapping = MutableMapping[
    str, Union[datetime, bool, str, List[str], List[int]]
]

oauth2_scheme = OAuth2PasswordBearer(
    tokenUrl=settings.security.TOKEN_LOGIN_URL
)


def authenticate(
    *,
    email: str,
    password: str,
    db: ActiveSession = ActiveSession,
) -> Optional[User]:
    user = db.query(User).filter(User.email == email).first()
    if not user:
        return None
    if not verify_password(password, user.hashed_password):
        return None
    return user


def create_access_token(*, sub: str) -> str:
    return _create_token(
        token_type="access_token",
        lifetime=timedelta(
            minutes=settings.security.ACCESS_TOKEN_EXPIRE_MINUTES
        ),
        sub=sub,
    )


def _create_token(
    token_type: str,
    lifetime: timedelta,
    sub: str,
) -> str:
    payload = {}
    expire = datetime.utcnow() + lifetime
    payload["type"] = token_type

    # https://datatracker.ietf.org/doc/html/rfc7519#section-4.1.3
    # The "exp" (expiration time) claim identifies the expiration time on
    # or after which the JWT MUST NOT be accepted for processing
    payload["exp"] = expire

    # The "iat" (issued at) claim identifies the time at which the
    # JWT was issued.
    payload["iat"] = datetime.utcnow()

    # The "sub" (subject) claim identifies the principal that is the
    # subject of the JWT
    payload["sub"] = str(sub)
    return jwt.encode(
        payload,
        settings.security.JWT_SECRET,
        algorithm=settings.security.ALGORITHM,
    )
