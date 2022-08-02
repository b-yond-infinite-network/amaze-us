'''
Inspirted by:
# ? source: https://testdriven.io/blog/flask-pytest/
'''
import logging

import pytest
from src import create_app, db
from src.populations import populate_drivers, populate_buses, populate_schedules, delete_all
from src.db_model.db_models import AvaiableSchedule, Driver, Schedule

log = logging.getLogger(__name__)


@pytest.fixture(scope='session')
def clean_start():
    log.info('deleting all ...')
    delete_all()


@pytest.fixture(scope='module')
def test_client():
    log.info('creating app ...')
    flask_app = create_app('tests/flask.yaml')

    with flask_app.test_client() as testing_client:
        with flask_app.app_context():
            yield testing_client


@pytest.fixture(scope='module')
def init_database(test_client):
    ''' initialize and populate datebase
    '''
    delete_all()
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


@pytest.fixture(scope='function')
def new_driver() -> dict:
    ''' pass a driver
    '''
    driver = dict(
        first_name='Blue',
        last_name='Max',
        email='blue.max@gmail.com',
        social_security_number=777333555,
    )

    yield dict(driver)

    Driver.query.filter_by(email=driver['email']).delete()


@pytest.fixture(scope='function')
def new_schedules() -> list[Schedule]:
    ''' pass a schedule
    '''
    n = 5
    scheds = AvaiableSchedule.query.limit(n).all()

    yield scheds

    for sched in scheds:
        Schedule.query.filter_by(id=sched.id).delete()


@pytest.fixture(scope='function')
def existing_schedules() -> list[Schedule]:
    ''' pass a schedule
    '''
    n = 5
    scheds = Schedule.query.limit(n).all()

    yield scheds

    for sched in scheds:
        Schedule.query.filter_by(id=sched.id).delete()
