
class GENERAL:
    PAGE_SIZE = 5
    DATETIME_FORMAT = "%d/%m/%Y %H:%M:%S"
    DATE_FORMAT = "%d/%m/%Y"
    FROM_EMAIL = 'Do not Reply <do_not_reply@gmail.com>'


class STATUS:
    SUCCESS = "SUCCESS"
    FAILURE = "FAILURE"
    ERROR = "ERROR"


class MESSAGE:
    SAVED_SUCCESSFULLY = "Saved Successfully"
    DELETED_SUCCESSFULLY = "Deleted Successfully"
    UPDATED_SUCCESSFULLY = "Updated Successfully"
    ACCESS_RIGHT_ERROR = "You don't have access right to complete this action"
    ITEM_NOT_FOUND = "Item Not Found"
    SIGNED_IN_SUCCESSFULLY = "Signed in successfully"
    SIGNED_UP_SUCCESSFULLY = "Signed up successfully"
    INVALID_CREDENTIALS = "Invalid credentials"
    NOT_ENOUGH_INFO = "NOT ENOUGH INFO"
    UNKNOWN_ERROR = "UNKNOWN_ERROR"
    USER_NOT_FOUND = "User not found"


class IMAGE_TYPE:
    IMAGE_PNG = "image/png"
    IMAGE_JPEG = "image/jpeg"
    IMAGE_JPG = "image/jpg"
    OPTIONS = (IMAGE_PNG, IMAGE_JPEG, IMAGE_JPG,)


class ERRORS:
    INTERNAL_ERROR = 0
    INVALID_DATA = 1
    UN_AUTHORIZED = 2
    MISSING_DATA = 3
    DOESNT_EXIST = 4
    CREATE_ERROR = 5
    UPDATE_ERROR = 6
    UPDATE_CREATE_ERROR = 7
    DELETE_ERROR = 8
    ALREADY_EXISTS = 9
    ACCOUNT_INACTIVE = 10
    ACCOUNT_SUSPENDED = 11
    SENDING_ERROR = 12
    PHONE_NOT_VERIFIED = 13
    INVALID_OAUTH_TOKEN = 14
    ACCOUNT_NOT_SUBSCRIBED = 15
    INVALID_TOKEN = 16
    INVALID_SYSTEM_DATA = 17
    NOT_EQUAL = 18
    PASSWORD_NOT_COMPLEX = 19


class Documents:
    PNG: str = 'PNG'
    JPG: str = 'JPG'
    JPEG: str = 'JPEG'
    JPE: str = 'JPE'
    PDF: str = 'PDF'
    SVG: str = 'SVG'

    FORMAT_CHOICES = (PNG, JPG, JPEG, JPE, PDF, SVG, )
