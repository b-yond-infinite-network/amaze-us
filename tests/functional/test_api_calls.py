'''
inspiration: # ? source: https://testdriven.io/blog/flask-pytest/
'''

import logging
import json
from datetime import timedelta
from random import randint, random

from src.constants.http_status_codes import (
    HTTP_200_OK,
    HTTP_201_CREATED,
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_409_CONFLICT
)


log = logging.getLogger(__name__)

PREFIX = 'api/v1'


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
    WHEN GET http://{{socket}}/{{PREFIX}}/schedule
    THEN chek that 200 is returned
    '''
    query_params = [
        "from=2022-01-01 00:00",
        "to=2022-12-03 00:00",
        "driver_id=2",
        "bus_id=2"
    ]

    for i in range(len(query_params)):
        query = query_params[0:i] + query_params[i + 1:]
        query = '?' + '&'.join(query)

        response = test_client.get(f'/{PREFIX}/schedule{query}')
        assert response.status_code == HTTP_200_OK


# GET TOP DRIVERS ###############################################

def test_get_top_drivers(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN GET http://{{socket}}/{{PREFIX}}/schedule
    THEN chek that 200 is returned and length of data == `N`
    '''
    N = 5
    response = test_client.get(
        f'/{PREFIX}/driver/top/{N}'
        '?from=2022-01-01 00:00'
        '&to=2022-02-03 00:00'
        '&page=1'
        '&per_page=100'
    )

    response_body = json.loads(response.data.decode('utf-8'))

    assert len(response_body['data']) == N
    assert response.status_code == HTTP_200_OK


# CONFLICTS #####################################################

def test_driver_duplicate_email(test_client, init_database, new_driver):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating a driver with same email address
    THEN chek that 409 is returned
    '''
    driver_clone = dict(new_driver)
    driver_clone['social_security_number'] += 1

    response_1 = test_client.post(f'/{PREFIX}/driver', json=new_driver)
    assert response_1.status_code == HTTP_201_CREATED

    response_2 = test_client.post(f'/{PREFIX}/driver', json=driver_clone)
    assert response_2.status_code == HTTP_409_CONFLICT


def test_driver_duplicate_ssn(test_client, init_database, new_driver):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating a driver with same ssn
    THEN chek that 409 is returned
    '''
    driver_clone = dict(new_driver)
    driver_clone['email'] = 'test.email@test.com'

    response_1 = test_client.post(f'/{PREFIX}/driver', json=new_driver)
    assert response_1.status_code == HTTP_201_CREATED

    response_2 = test_client.post(f'/{PREFIX}/driver', json=driver_clone)
    assert response_2.status_code == HTTP_409_CONFLICT


# SCHEDULE CONFLICTS ############################################

def test_schedule_overlap(test_client, init_database, new_schedules):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating schedules that are ensured to have no overlap
    THEN chek that 200 is returned
    '''
    scheds = list(map(lambda sched: sched.as_dict(), new_schedules))
    list(map(lambda sched: sched.pop('id'), scheds))

    for sched in scheds:
        response_i = test_client.post(f'/{PREFIX}/schedule', json=sched)
        assert response_i.status_code == HTTP_201_CREATED


def test_schedule_overlap_2(test_client, init_database, existing_schedules):
    '''
    GIVEN a Flask application configured for testing
    WHEN creating schedules that overlap with the existing schedules
    THEN chek that 409 is returned
    '''
    # * add random time on dt_start and dt_end
    for sched in existing_schedules:
        if random() > 0.5:
            sched.dt_start += timedelta(minutes=randint(0, 5))
        else:
            sched.dt_start -= timedelta(minutes=randint(0, 5))

        if random() > 0.5:
            sched.dt_end += timedelta(minutes=randint(0, 5))
        else:
            sched.dt_end -= timedelta(minutes=randint(0, 5))

    scheds = list(map(lambda sched: sched.as_dict(), existing_schedules))
    list(map(lambda sched: sched.pop('id'), scheds))

    for sched in scheds:
        response_i = test_client.post(f'/{PREFIX}/schedule', json=sched)
        assert response_i.status_code == HTTP_409_CONFLICT


# ITEM NOT FOUND ################################################

def test_item_not_found(test_client, init_database):
    '''
    GIVEN a Flask application configured for testing
    WHEN getting items that are not in db
    THEN chek that 404 is returned
    '''
    response = test_client.get(f'/{PREFIX}/driver/{int(1e20)}')
    assert response.status_code == HTTP_404_NOT_FOUND

    response = test_client.get(f'/{PREFIX}/schedule/{int(1e20)}')
    assert response.status_code == HTTP_404_NOT_FOUND

    response = test_client.get(f'/{PREFIX}/bus/{int(1e20)}')
    assert response.status_code == HTTP_404_NOT_FOUND


# BAD INPUT FOR DRIVER ##########################################

def test_bad_email(test_client, init_database, new_driver):
    '''
    GIVEN a Flask application configured for testing
    WHEN inserting a driver with bad email
    THEN chek that 400 is returned
    '''
    invalid_emails = [
        'gmail.com',
        'gmail@com.',
        'not[]email@gmail.com',
        'not()email@gmail.com',
        'not"n"email@gmail',
        'not/email@gmail'
    ]

    for email in invalid_emails:
        new_driver['email'] = email

        response = test_client.post(f'/{PREFIX}/driver', json=new_driver)
        assert response.status_code == HTTP_400_BAD_REQUEST


def test_bad_name(test_client, init_database, new_driver):
    '''
    GIVEN a Flask application configured for testing
    WHEN inserting a driver with bad email
    THEN chek that 400 is returned
    '''
    invalid_names = [
        '12',
        '  ',
        '+-',
        'w.e.i.r.d',
        'space in name',
        'coolname!',
        'number1',
    ]

    for name in invalid_names:
        copy = dict(new_driver)
        if random() > 0.5:
            copy['first_name'] = name
        else:
            copy['last_name'] = name

        response = test_client.post(f'/{PREFIX}/driver', json=copy)
        assert response.status_code == HTTP_400_BAD_REQUEST
