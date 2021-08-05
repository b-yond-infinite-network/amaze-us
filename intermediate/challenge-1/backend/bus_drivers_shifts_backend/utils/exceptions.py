
from rest_framework import status
from . import constants


class CustomException(Exception):

    def __init__(self, message="Something went wrong.", status=status.HTTP_400_BAD_REQUEST, code=constants.ERRORS.INTERNAL_ERROR):
        super().__init__()
        self.message = message
        self.status = status
        self.code = code


def invalidData(arg):
    return CustomException(
        message='%s' % (arg),
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.INVALID_DATA
    )


def unAuthorized():
    return CustomException(
        message="You are not authorized to do this action",
        status=status.HTTP_403_FORBIDDEN,
        code=constants.ERRORS.UN_AUTHORIZED
    )


def missingFields(missings):
    return CustomException(
        message='Missing [' +
        '] or ['.join([', '.join(x) for x in missings]) + ']',
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.MISSING_DATA
    )


def doesntExist(name='object'):
    return CustomException(
        message='%s does not exist' % (name),
        status=status.HTTP_403_FORBIDDEN,
        code=constants.ERRORS.DOESNT_EXIST
    )


def createError(name=''):
    return CustomException(
        message="create error %s" % (name),
        code=constants.ERRORS.CREATE_ERROR
    )


def updateError(name=''):
    return CustomException(
        message="update error %s" % (name),
        code=constants.ERRORS.UPDATE_ERROR
    )


def updateOrCreateError(name=''):
    return CustomException(
        message="update or create error %s" % (name),
        code=constants.ERRORS.UPDATE_CREATE_ERROR
    )


def deleteError(name=''):
    return CustomException(
        message="delete error %s" % (name),
        code=constants.ERRORS.DELETE_ERROR
    )


def alreadyExists(name=''):
    return CustomException(
        message='already exists %s' % (name),
        status=status.HTTP_409_CONFLICT,
        code=constants.ERRORS.ALREADY_EXISTS
    )


def accountInActive():
    return CustomException(
        message='User not active',
        status=status.HTTP_403_FORBIDDEN,
        code=constants.ERRORS.ACCOUNT_INACTIVE
    )


def accountSuspended(name=''):
    return CustomException(
        message='Account is suspended %s' % (name),
        status=status.HTTP_403_FORBIDDEN,
        code=constants.ERRORS.ACCOUNT_SUSPENDED
    )


def incorrectPassword():
    return CustomException(
        message='Incorrect password',
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.INVALID_DATA
    )


def sendingError(name='email'):
    return CustomException(
        message="Could not send %s" % (name),
        status=status.HTTP_500_INTERNAL_SERVER_ERROR,
        code=constants.ERRORS.SENDING_ERROR
    )


def notImplemented(name):
    return CustomException(
        message='{0} does not implement abstract methods and fields.'.format(
            name),
        status=status.HTTP_500_INTERNAL_SERVER_ERROR,
        code=constants.ERRORS.INTERNAL_ERROR
    )


def invalidToken():
    return CustomException(
        message='invalid access token.',
        status=status.HTTP_401_UNAUTHORIZED,
        code=constants.ERRORS.INVALID_TOKEN
    )


def invalidSystemData(name=''):
    return CustomException(
        message='invalid  %s' % (name),
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.INVALID_SYSTEM_DATA
    )


def notEqual(arg1='', arg2=''):
    return CustomException(
        message='Not Equal %s %s.' % (arg1, arg2),
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.NOT_EQUAL
    )


def passwordNotComplex():
    return CustomException(
        message='Password not complex enough',
        status=status.HTTP_400_BAD_REQUEST,
        code=constants.ERRORS.PASSWORD_NOT_COMPLEX
    )
