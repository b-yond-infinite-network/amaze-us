from sqlalchemy import Column, Integer, String
from app.config.database import Base


class BusModel(Base):
    # Represents the bus db table.
    __tablename__ = "bus"

    id = Column(Integer, primary_key=True)
    capacity = Column(Integer)
    model = Column(String(100))
    maker = Column(String(100))
    driver_id = Column(Integer)
