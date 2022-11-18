import pydantic


class Bus(pydantic.BaseModel):
    class Config:
        orm_mode = True

    id: int
    capacity: int
    model: str
    make: str
