from fastapi import status

from .app_exception import AppException


class DBException(AppException):
    def __init__(
        self,
        columns=None,
        inner_exception=None,
        value=None,
        is_fk_error: bool = False,
    ):
        self.columns = columns
        self.details = inner_exception
        self.data = value
        self.fk_error = is_fk_error

        self.message = str(self)
        self.status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
        self.error = True

    def __str__(self):
        if self.fk_error:
            return "An error occurred while trying to save the entity's foreign key"

        return "Duplicate key for columns %s" % (self.columns,)
