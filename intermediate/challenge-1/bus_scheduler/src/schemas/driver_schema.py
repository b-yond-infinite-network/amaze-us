from typing import Optional, Sequence

from pydantic import BaseModel, EmailStr

from .user_schema import UserBase


class DriverBase(UserBase):
    ssn: str


class DriverUpdate(DriverBase):
    first_name: Optional[str]
    last_name: Optional[str]
    ssn: Optional[str]
    email: Optional[EmailStr]


class DriverCreate(DriverBase):
    ...


class DriverInDBBase(DriverBase):
    id: int

    class Config:
        orm_mode = True


class Driver(DriverInDBBase):
    pass


# Properties properties stored in DB
class DriverInDB(DriverInDBBase):
    pass
