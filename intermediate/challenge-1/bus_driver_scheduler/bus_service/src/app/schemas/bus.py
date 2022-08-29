from typing import Optional

from pydantic import BaseModel, Field

# Represents the application data or domain.


class BusSchema(BaseModel):
    # The pydantic Fields is used for data validation.
    capacity: int = Field(gt=0)
    model: str = Field(max_length=100)
    maker: str = Field(max_length=100)
    driver_id: int


class BusDb(BusSchema):
    id: int

    # orm means it will be pulled from the db and can be mapped to a system domain.
    # https://pydantic-docs.helpmanual.io/usage/models/#orm-mode-aka-arbitrary-class-instances
    class Config:
        orm_mode = True
