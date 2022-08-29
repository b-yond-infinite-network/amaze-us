from sqlalchemy import Column, Integer, String
from app.config.database import Base


class DriverModel(Base):
    # Represents the driver db table.
    __tablename__ = "driver"

    id = Column(Integer, primary_key=True)
    first_name = Column(String(100))
    last_name = Column(String(100))
    social_security_number = Column(Integer)
    email = Column(String(100))
