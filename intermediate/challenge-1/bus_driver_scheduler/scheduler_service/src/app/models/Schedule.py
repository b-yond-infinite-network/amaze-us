from sqlalchemy import Column, Integer, Date, String, Time, DateTime
from app.config.database import Base


class ScheduleModel(Base):
    # Represents the schedule db table.
    __tablename__ = "schedule"

    id = Column(Integer, primary_key=True)
    bus_id = Column(Integer)
    driver_id = Column(Integer)
    start_dt = Column(DateTime)
    end_dt = Column(DateTime)
