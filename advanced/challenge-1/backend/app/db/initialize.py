import logging
from datetime import datetime, timedelta
from random import choice, randint, randrange

from faker import Faker
from sqlalchemy.exc import IntegrityError, PendingRollbackError
from sqlalchemy.orm import Session

from . import models
from .crud import get_user_by_email
from app.auth.authentication import get_password_hash


logger = logging.getLogger(__name__)


def initialize_db(db: Session):
    '''Provided test data:
    - +1000 (1K) unique drivers
    - +250 unique buses
    - +1000000 (1M) schedules over 3 months period'''

    email = 'manager@amazeus.com'
    admin = get_user_by_email(db, email)
    if admin:
        logger.info(f'Database is already initialized, moving on')
        return

    logger.info(
        'Populating the database with test data (should take aprox. 2 min)')

    password = 'manager'
    logger.info(f'Creating default manager user: {email}/{password}')
    hash = get_password_hash(password)
    logger.info(f'Hash generated for {email} is {hash}')
    admin = models.User(
        email=email,
        hash=hash,
        scope='manager'
    )
    db.add(admin)

    email = 'employee@amazeus.com'
    password = 'employee'
    logger.info(f'Creating default employee user: {email}/{password}')
    hash = get_password_hash(password)
    logger.info(f'Hash generated for {email} is {hash}')
    employee = models.User(
        email=email,
        hash=hash,
        scope='employee'
    )
    db.add(employee)

    db.commit()

    fake = Faker()

    drivers = []
    n = 1000
    logger.info(f'Creating {n} drivers...')
    for i in range(0, n):
        logger.debug(f'Driver {i} of {n}')
        drivers.append(add_driver(db, fake))
    db.commit()

    for driver in drivers:
        db.refresh(driver)

    buses = []
    n = 250
    logger.info(f'Creating {n} buses...')
    for i in range(0, n):
        logger.debug(f'Creating bus {i} of {n}')
        buses.append(add_bus(db, fake))
    db.commit()

    for bus in buses:
        db.refresh(bus)

    schedules = []
    n = 1_000_000
    logger.info(f'Creating {n} schedules...')
    for i in range(0, n):
        logger.debug(f'Creating schedule {i} of {n}')
        rand = randrange(3 * 30 * 24 * 60)
        mins = timedelta(minutes=rand)
        schedules.append(models.Schedule(bus=choice(buses),
                                         driver=choice(drivers),
                                         timestamp=datetime.now() + mins,
                                         origin=fake.last_name(),
                                         destination=fake.last_name(),
                                         distance=rand))

        if i % 100_000 == 0:  # commit every 100k records
            db.add_all(schedules)
            db.commit()
            schedules = []

    return


def add_driver(db, fake):
    added = False
    driver = None
    while not added:
        try:
            first_name = fake.first_name()
            last_name = fake.last_name()
            email = f'{first_name}{last_name + str(randrange(10000))}@example.com'
            driver = models.Driver(first_name=first_name,
                                   last_name=last_name,
                                   ssn=fake.ssn(),
                                   email=email)
            db.add(driver)
            db.commit()
            added = True
        except (IntegrityError, PendingRollbackError) as error:
            logger.warn('Unique constraint violation, retrying')
            pass
    return driver


def add_bus(db, fake):
    added = False
    bus = None
    while not added:
        try:
            bus = models.Bus(capacity=choice([20, 30, 40, 50]),
                             model=fake.last_name() + str(randrange(10000)),
                             make=fake.company())
            db.add(bus)
            db.commit()
            added = True
        except (IntegrityError, PendingRollbackError) as error:
            logger.warn('Unique constraint violation, retrying')
            pass
    return bus
