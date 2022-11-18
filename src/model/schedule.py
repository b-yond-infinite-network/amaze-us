#A schedule links the different buses with drivers on a given day/time
import databases
import datetime

import ormar

from src.model.bus import Bus
from src.model.driver import Driver
from src.model.metadata import metadata

database = databases.Database("sqlite:///db.sqlite")

class Schedule(ormar.Model):
    class Meta:
        tablename = "schedule"
        metadata = metadata
        database = database


    id: int = ormar.Integer(primary_key=True)
    driver: Driver = ormar.ForeignKey(Driver)
    bus: Bus = ormar.ForeignKey(Bus)
    begin: datetime = ormar.DateTime()
    end: datetime = ormar.DateTime()
