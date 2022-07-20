# The password of users is 123456
from src.core.security import get_password_hash

USER_PASSWORD = "123456"

USERS = [
    {
        "first_name": "admin",
        "last_name": "app",
        "email": "admin@admin.com",
        "is_superuser": True,
        "hashed_password": get_password_hash(USER_PASSWORD),
    },
    {
        "first_name": "user",
        "last_name": "app",
        "email": "user@user.com",
        "is_superuser": False,
        "hashed_password": get_password_hash(USER_PASSWORD),
    },
]
