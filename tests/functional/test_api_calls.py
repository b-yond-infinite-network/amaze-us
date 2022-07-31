'''
inspiration: # ? source: https://testdriven.io/blog/flask-pytest/
'''

import logging
import json
from datetime import datetime

from src.constants.http_status_codes import (
    HTTP_200_OK,
    HTTP_201_CREATED,
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_409_CONFLICT
)


log = logging.getLogger(__name__)

prefix = 'api/v1'
flask_test_config = 'volume/config/flask_test.yml'


# BASIC #########################################################

def test_home_page(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN the '/' page is requested (GET)
    THEN check that the response is valid
    '''
    response = test_client.get('/')
    assert response.status_code == HTTP_200_OK
    assert b'{"test":"good"}' in response.data


# GET BUS SCHEDULES #############################################

def test_get_schedules_all_args(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned 
    '''
    response = test_client.get(
        f'/{prefix}/schedule'
        '?from=2022-01-01 00:00'
        '&to=2022-02-03 00:00'
        '&driver_id=999'
    )
    assert response.status_code == HTTP_200_OK


# GET TOP DRIVERS ###############################################

def test_get_top_drivers(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{prefix}}/schedule
    THEN chek that 200 is returned and length of data == `N`
    '''
    N = 5

    response = test_client.get(
        f'/{prefix}/driver/top/{N}'
        '?from=2022-01-01 00:00'
        '&to=2022-02-03 00:00'
        '&page=1'
        '&per_page=100'
    )

    response_body = json.loads(response.data.decode('utf-8'))

    assert len(response_body['data']) == N
    assert response.status_code == HTTP_200_OK


# CONFLICTS #####################################################

def test_driver_duplicate_email(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating a driver with same email address
    THEN chek that 409 is returned
    '''
    driver1 = dict(
        first_name='Blue',
        last_name='Max',
        email='blue.max@gmail.com',
        social_security_number=333555777,
    )
    driver2 = dict(driver1)
    driver2.update(dict(
        social_security_number=999888333
    ))

    response_1 = test_client.post(f'/{prefix}/driver', json=driver1)
    assert response_1.status_code == HTTP_201_CREATED

    response_2 = test_client.post(f'/{prefix}/driver', json=driver2)
    assert response_2.status_code == HTTP_409_CONFLICT


def test_driver_duplicate_ssn(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating a driver with same ssn
    THEN chek that 409 is returned
    '''
    driver1 = dict(
        first_name='Red',
        last_name='Max',
        email='red.max@gmail.com',
        social_security_number=888333111,
    )
    driver2 = dict(driver1)
    driver2['email'] = 'brown.max@gmail.com'

    response_1 = test_client.post(f'/{prefix}/driver', json=driver1)
    assert response_1.status_code == HTTP_201_CREATED

    response_2 = test_client.post(f'/{prefix}/driver', json=driver2)
    assert response_2.status_code == HTTP_409_CONFLICT


# SCHEDULE CONFLICTS ############################################

def test_schedule_overlap(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating a driver with same ssn
    THEN chek that 409 is returned
    '''
    response = test_client.get(f'/{prefix}/available_schedule', query_string={
        'per_page': 1
    })
    response_body = json.loads(response.data.decode('utf-8'))
    log.critical()
    available_sched = response_body['data'][0]

    # TODO ADD TO DATETIME
