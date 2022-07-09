from typing import Optional

from pydantic import BaseModel, EmailStr


class DriverBase(BaseModel):
    first_name: str
    last_name: str
    email: EmailStr


class DriverCreate(DriverBase):
    pass


class DriverUpdate(BaseModel):
    first_name: Optional[str]
    last_name: Optional[str]
    email: Optional[EmailStr]


class Driver(DriverBase):
    id: int

    class Config:
        orm_mode = True
