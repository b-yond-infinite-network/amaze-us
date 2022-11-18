import pydantic


class Driver(pydantic.BaseModel):
    class Config:
        orm_mode = True

    id: int
    first_name: str
    last_name: str
    social_security_number: str
    email: str

class TopDriver(Driver):
    class Config:
        orm_mode = True

    schedule_count: int