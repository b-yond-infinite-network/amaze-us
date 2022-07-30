'''
inspiration: # ? source: https://testdriven.io/blog/flask-pytest/
'''

import logging

from src import create_app
from src.constants.http_status_codes import HTTP_200_OK, HTTP_405_METHOD_NOT_ALLOWED

log = logging.getLogger(__name__)

prefix = 'api/v1'
flask_test_config = 'volume/config/flask_test.yml'


def test_home_page():
    '''
    GIVEN a Flask application configured for testing
    WHEN the '/' page is requested (GET)
    THEN check that the response is valid
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get('/')
        assert response.status_code == HTTP_200_OK
        assert b'{"test":"good"}' in response.data


def test_home_page_post():
    '''
    GIVEN a Flask application configured for testing
    WHEN the '/' page is is posted to (POST)
    THEN check that 405 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.post('/')
        assert response.status_code == HTTP_405_METHOD_NOT_ALLOWED


# GET BUS SCHEDULES #############################################

def test_get_schedules_all_args():
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get(
            f'/{prefix}/schedule'
            '?from=2022-01-01 00:00'
            '&to=2022-02-03 00:00'
            '&driver_id=999'
        )
        assert response.status_code == HTTP_200_OK


def test_get_schedules_no_upper_time_bound():
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get(
            f'/{prefix}/schedule'
            '?from=2022-01-01 00:00'
            '&driver_id=999'
        )
        assert response.status_code == HTTP_200_OK


def test_get_schedules_no_lower_time_bound():
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get(
            f'/{prefix}/schedule'
            '?to=2022-02-03 00:00'
            '&driver_id=999'
        )
        assert response.status_code == HTTP_200_OK


def test_get_schedules_no_driver_id():
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get(
            f'/{prefix}/schedule'
            '?from=2022-01-03 00:00'
            '&to=2022-02-03 00:00'
        )
        assert response.status_code == HTTP_200_OK


# GET TOP DRIVERS ###############################################

def test_get_top_drivers():
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned
    '''
    flask_app = create_app(flask_test_config)

    with flask_app.test_client() as test_client:
        response = test_client.get(
            f'/{prefix}/driver/top/5'
            '?from=2022-01-01 00:00'
            '&to=2022-02-03 00:00'
            '&page=1'
            '&per_page=100'
        )

        import json
        data = response.data.decode('utf-8')
        assert response.status_code == HTTP_200_OK
