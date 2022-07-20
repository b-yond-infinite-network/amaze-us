from fastapi import status


class AuthException(Exception):
    def __init__(
        self,
        message: str = "Only system admins user can execute this operation",
        status_code=status.HTTP_401_UNAUTHORIZED,
    ):
        self.message = message
        self.status_code = status_code

    def __iter__(self):
        for key, value in self.__dict__.items():
            yield key, value
