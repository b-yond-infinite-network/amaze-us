import logging
from base64 import b64encode
from datetime import datetime, timedelta
from random import choice, randbytes, randrange

from faker import Faker
from passlib.crypto.digest import pbkdf2_hmac
from sqlalchemy.exc import IntegrityError, PendingRollbackError
from sqlalchemy.orm import Session

from . import models, schemas


logger = logging.getLogger(__name__)


def get_user(db: Session, user_id: int):
    return db.query(models.User).filter(models.User.id == user_id).first()


def get_user_by_email(db: Session, email: str):
    return db.query(models.User).filter(models.User.email == email).first()


def get_users(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.User).offset(skip).limit(limit).all()


def create_user(db: Session, user: schemas.UserCreate):
    fake_hashed_password = user.password + "notreallyhashed"
    db_user = models.User(
        email=user.email, hashed_password=fake_hashed_password)
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user


def get_drivers(db: Session, skip: int = 0, limit: int = 100):
    return db.query(models.Driver).offset(skip).limit(limit).all()


def create_driver(db: Session, driver: schemas.DriverCreate):
    db_item = models.Driver(**driver.dict())
    db.add(db_item)
    db.commit()
    db.refresh(db_item)
    return db_item


def initialize_db(db: Session):
    '''Provided test data:
    - +1000 (1K) unique drivers
    - +250 unique buses
    - +1000000 (1M) schedules over 3 months period'''

    email = 'admin@admin.com'
    admin = get_user_by_email(db, email)
    if admin:
        logger.info(f'Database is already initialized, moving on')
        return

    logger.info(
        'Populating the database with test data (should take aprox. 2 min)')

    password = 'admin'
    logger.info(f'Creating default user: {email}/{password}')
    salt = b64encode(randbytes(256))
    hash = pbkdf2_hmac('sha256', b64encode(
        password.encode()), salt, 100_000, 256)
    admin = models.User(email=email, hash=hash, salt=salt)
    db.add(admin)
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
        random_minutes = timedelta(minutes=randrange(3 * 30 * 24 * 60))
        schedules.append(models.Schedule(bus=choice(buses),
                                         driver=choice(drivers),
                                         timestamp=datetime.now() + random_minutes))

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
