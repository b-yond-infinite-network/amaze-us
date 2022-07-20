from datetime import date, time
from typing import Any

from sqlalchemy import Column, Date, ForeignKey, Index, Integer, Time
from sqlalchemy.orm import relationship, validates

from src.database.db import Base


class Schedule(Base):
    __tablename__ = "schedules"

    id = Column(Integer, primary_key=True, index=True)
    day = Column(Date)
    start_hour = Column(Time)
    end_hour = Column(Time)
    driver_id = Column(
        Integer,
        ForeignKey("drivers.id", onupdate="CASCADE"),
        index=True,
    )
    bus_id = Column(
        Integer,
        ForeignKey("buses.id", onupdate="CASCADE"),
        index=True,
    )

    drivers = relationship(
        "Driver", back_populates="schedules", lazy="joined"
    )
    buses = relationship("Bus", back_populates="schedules")

    Index(
        "schedule_idx_day_hour_driver_bus",
        day,
        start_hour,
        end_hour,
        driver_id,
        bus_id,
        unique=True,
    )
    Index(
        "schedule_idx_day_hour_driver",
        day,
        start_hour,
        end_hour,
        driver_id,
        unique=True,
    )
    Index(
        "schedule_idx_day_hour_bus",
        day,
        start_hour,
        end_hour,
        bus_id,
        unique=True,
    )

    @validates("day")
    def __validate_day(self, key, value: Any) -> date:
        if isinstance(value, date):
            return value
        return date(*map(int, value.split("-")))

    @validates("start_hour", "end_hour")
    def __validate_hours(self, key, value: Any) -> time:
        if isinstance(value, time):
            return value
        return time(*map(int, value.split(":")))
