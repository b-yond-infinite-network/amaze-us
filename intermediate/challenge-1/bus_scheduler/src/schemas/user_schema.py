from typing import Optional

from pydantic import BaseModel, EmailStr


class UserBase(BaseModel):
    first_name: Optional[str]
    last_name: Optional[str]
    email: EmailStr


# Properties to receive via API on creation
class UserCreate(UserBase):
    is_superuser: bool = False
    email: EmailStr
    password: str


# Properties to receive via API on update
class UserUpdate(UserBase):
    first_name: Optional[str]
    last_name: Optional[str]
    email: Optional[EmailStr]
    is_superuser: Optional[bool]
    password: Optional[str]


class UserInDBBase(UserBase):
    id: int

    class Config:
        orm_mode = True


# Additional properties stored in DB but not returned by API
class UserInDB(UserInDBBase):
    hashed_password: str


# Additional properties to return via API
class User(UserInDBBase):
    ...
