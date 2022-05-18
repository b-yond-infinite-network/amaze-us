from typing import Optional

from pydantic import BaseModel



class BusBase(BaseModel):
    model: str
    make: str
    capacity: int



class BusCreate(BusBase):
    pass


class BusUpdate(BaseModel):
    model: Optional[str]
    make: Optional[str]
    capacity: Optional[int]


class Bus(BusBase):
    id: int

    class Config:
        orm_mode = True


class BusGet(BaseModel):
    id: int
    model: Optional[str]
    make: Optional[str]
    capacity: Optional[int]

    class Config:
        orm_mode = True
