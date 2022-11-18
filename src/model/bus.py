import ormar

from src.model.metadata import metadata, database


class Bus(ormar.Model):
    class Meta:
        tablename = "bus"
        metadata = metadata
        database = database

    id: int = ormar.Integer(primary_key=True)
    capacity: int = ormar.Integer()
    model: str = ormar.String(max_length=255)
    make: str = ormar.String(max_length=255)
