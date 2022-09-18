from sqlalchemy import Column, ForeignKey, Integer, DateTime, String
from sqlalchemy.orm import relationship

from .database import Base


class User(Base):
    __tablename__ = 'user'

    id = Column(Integer, primary_key=True, index=True)
    email = Column(String, unique=True, index=True)
    hash = Column(String, nullable=False)
    salt = Column(String, nullable=False)
    scope = Column(String, nullable=False, default='employee')


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
