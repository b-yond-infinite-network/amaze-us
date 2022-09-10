from datetime import datetime, timedelta
from random import choice, randbytes, randrange

from faker import Faker
from passlib.crypto.digest import pbkdf2_hmac
from sqlalchemy import Column, ForeignKey, Integer, DateTime, String
from sqlalchemy.orm import relationship, Session
from sqlalchemy.exc import IntegrityError, PendingRollbackError

from .database import Base


class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True, index=True)
    email = Column(String, unique=True, index=True)
    hash = Column(String, nullable=False)
    salt = Column(String, nullable=False)


class Driver(Base):
    '''A driver has a first name, last name, social security number and an email.'''
    __tablename__ = 'driver'

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String, nullable=False)
    last_name = Column(String, nullable=False)
    ssn = Column(String, nullable=False, unique=True)
    email = Column(String, nullable=False, unique=True, index=True)

    schedules = relationship('Schedule', back_populates='driver')


class Bus(Base):
    '''A bus has a capacity, a model, make, and an associated driver.'''
    __tablename__ = 'bus'

    id = Column(Integer, primary_key=True, index=True)
    capacity = Column(Integer, nullable=False)
    model = Column(String, nullable=False, unique=True)
    make = Column(String, nullable=False)

    schedules = relationship('Schedule', back_populates='bus')


class Schedule(Base):
    '''A schedule links the different buses with drivers on a given day/time.'''
    __tablename__ = 'schedule'

    id = Column(Integer, primary_key=True, index=True)
    bus_id = Column(Integer, ForeignKey('bus.id'), nullable=False)
    driver_id = Column(Integer, ForeignKey('driver.id'), nullable=False)
    timestamp = Column(DateTime, nullable=False)

    bus = relationship('Bus', back_populates='schedules')
    driver = relationship('Driver', back_populates='schedules')


def initialize_db(db: Session):
    '''Provided test data:
    - +1000 (1K) unique drivers
    - +250 unique buses
    - +1000000 (1M) schedules over 3 months period'''

    salt = randbytes(256)
    hash = str.decode(pbkdf2_hmac('sha256', str.encode('admin'), salt, 100_000, 256))
    admin = User(email='admin@admin.com', hash=hash, salt=salt)
    db.add(admin)
    db.commit()

    fake = Faker()

    drivers = []
    for i in range(0, 1000):
        drivers.append(add_driver(db, fake))
    db.commit()

    for driver in drivers:
        db.refresh(driver)

    buses = []
    for i in range(0, 250):
        buses.append(add_bus(db, fake))
    db.commit()

    for bus in buses:
        db.refresh(bus)

    schedules = []
    for i in range(0, 100_000_000):
        random_minutes = timedelta(minutes=randrange(3 * 30 * 24 * 60))
        schedules.append(Schedule(bus=choice(buses),
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
            email = f'{first_name}{last_name + str(randrange(1000))}@example.com'
            driver = Driver(first_name=first_name,
                            last_name=last_name,
                            ssn=fake.ssn(),
                            email=email)
            db.add(driver)
            db.commit()
            added = True
        except (IntegrityError, PendingRollbackError) as error:
            pass
    return driver


def add_bus(db, fake):
    added = False
    bus = None
    while not added:
        try:
            bus = Bus(capacity=choice([20, 30, 40, 50]),
                      model=fake.last_name() + str(randrange(1000)),
                      make=fake.company())
            db.add(bus)
            db.commit()
            added = True
        except (IntegrityError, PendingRollbackError) as error:
            pass
    return bus
