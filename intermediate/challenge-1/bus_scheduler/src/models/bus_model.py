from sqlalchemy import Column, ForeignKey, Index, Integer, String
from sqlalchemy.orm import relationship

from src.database.db import Base


class Bus(Base):
    __tablename__ = "buses"

    id = Column(Integer, primary_key=True, index=True)
    capacity = Column(Integer, index=True)
    model = Column(String, index=True)
    make = Column(String, index=True)
    driver_id = Column(
        Integer,
        ForeignKey("drivers.id"),
        index=True,
    )

    Index(
        "bus_idx_capacity_model_make_driver",
        capacity,
        model,
        make,
        driver_id,
        unique=True,
    )
    Index("bus_idx_capacity_model_make", capacity, model, make, unique=True)
    Index("bus_idx_model_make_driver", model, make, driver_id, unique=True)

    driver = relationship("Driver", back_populates="buses", lazy="joined")
    schedules = relationship("Schedule", back_populates="buses")
