from datetime import datetime
from pydantic import BaseModel


class UserBase(BaseModel):
    email: str


class UserCreate(UserBase):
    password: str


class User(UserBase):
    id: int

    class Config:
        orm_mode = True


class DriverBase(BaseModel):
    first_name: str
    last_name: str
    ssn: str
    email: str


class DriverCreate(DriverBase):
    pass


class Driver(DriverBase):
    id: int

    class Config:
        orm_mode = True


class BusBase(BaseModel):
    capacity: int
    make: str
    model: str


class BusCreate(BusBase):
    pass


class Bus(BusBase):
    id: int
    driver: Driver = None

    class Config:
        orm_mode = True


class ScheduleBase(BaseModel):
    timestamp: datetime


class ScheduleCreate(ScheduleBase):
    pass


class Schedule(ScheduleBase):
    id: int
    driver: Driver
    bus: Bus

    class Config:
        orm_mode = True
