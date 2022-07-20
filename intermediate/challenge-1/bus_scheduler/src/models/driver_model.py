from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship

from src.database.db import Base


class Driver(Base):
    __tablename__ = "drivers"

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String, index=True)
    last_name = Column(String, index=True)
    ssn = Column(String, index=True, unique=True)
    email = Column(String, index=True, unique=True)

    buses = relationship("Bus", back_populates="driver", lazy="joined")
    schedules = relationship(
        "Schedule", back_populates="drivers"
    )
