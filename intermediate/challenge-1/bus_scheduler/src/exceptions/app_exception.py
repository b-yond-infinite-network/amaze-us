from typing import Any

from fastapi import status

from src.config import settings


class AppException(Exception):
    def __init__(
        self,
        message: str,
        details: str = None,
        data: Any = None,
        error: bool = None,
        status_code: status = status.HTTP_400_BAD_REQUEST,
    ):
        self.message = message
        self.status_code = status_code
        self.details = details
        self.data = data
        self.error = error

    def __iter__(self):
        for key, value in self.__dict__.items():
            if value is None:
                continue
            if isinstance(value, Exception):
                if settings.current_env in ["develop", "testing"]:
                    yield key, "{}".format(value.__dict__)
                continue

            yield key, value
