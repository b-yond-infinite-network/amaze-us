import databases

import ormar

from src.model.metadata import metadata

database = databases.Database("sqlite:///db.sqlite")


class Driver(ormar.Model):
    class Meta:
        tablename = "drivers"
        metadata = metadata
        database = database

    id: int = ormar.Integer(primary_key=True)
    first_name: str = ormar.String(max_length=255)
    last_name: str = ormar.String(max_length=255)
    social_security_number = ormar.String(max_length=255)
    email = ormar.String(max_length=255,
        regex="([A-Za-z0-9]+[.-_])*[A-Za-z0-9]+@[A-Za-z0-9-]+(\.[A-Z|a-z]{2,})+")
