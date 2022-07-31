'''
Inspirted by:
# ? source: https://testdriven.io/blog/flask-pytest/
'''
import logging

import pytest
from src import create_app, db
from src.populations import populate_drivers, populate_buses, populate_schedules, delete_all
from src.db_model.db_models import Bus, Driver

log = logging.getLogger(__name__)


@pytest.fixture(scope='module')
def test_client():
    log.info('creating app ...')
    flask_app = create_app('volume/config/flask_test.yml')

    with flask_app.test_client() as testing_client:
        with flask_app.app_context():
            yield testing_client


@pytest.fixture(scope='module')
def init_database(test_client):
    ''' initialize and populate datebase
    '''
    db.create_all()
    db.session.commit()

    log.info('populating Bus table ...')
    populate_buses(5)

    log.info('populating Driver table ...')
    populate_drivers(10)

    log.info('populating Schedule table ...')
    populate_schedules('2022-01-01', '2022-02-01')

    # * POPULATE FOR EACH MODULE IN PYTEST
    db.session.commit()

    yield

    # * DELETE ROWS AFTER EACH MODULE IN PYTEST
    delete_all()


@pytest.fixture(scope='module')
def new_bus() -> dict:
    ''' pass a bus
    '''
    return dict(
        make='TEST',
        mode='T123'
    )


@pytest.fixture(scope='module')
def new_driver() -> dict:
    ''' pass a driver
    '''
    return dict(
        first_name='Blue',
        last_name='Max',
        email='blue.max@gmail.com',
        social_security_number=333555777,
    )
