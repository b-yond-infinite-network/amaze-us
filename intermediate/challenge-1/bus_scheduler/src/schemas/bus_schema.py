from typing import Optional

from pydantic import BaseModel


class BusBase(BaseModel):
    capacity: int
    model: str
    make: str
    driver_id: Optional[int]


class BusUpdate(BusBase):
    capacity: Optional[int]
    model: Optional[str]
    make: Optional[str]


class BusCreate(BusBase):
    ...


class BusInDBBase(BusBase):
    id: int

    class Config:
        orm_mode = True


class Bus(BusInDBBase):
    pass


# Properties properties stored in DB
class BusInDB(BusInDBBase):
    pass
