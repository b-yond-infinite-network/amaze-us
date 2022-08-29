from pydantic import BaseModel, Field, EmailStr

# Represents the system data or domain.


class DriverSchema(BaseModel):
    first_name: str = Field(max_length=100)
    last_name: str = Field(max_length=100)
    social_security_number: int = Field(gt=0)
    email: EmailStr


class DriverDb(DriverSchema):
    id: int

    class Config:
        orm_mode = True
