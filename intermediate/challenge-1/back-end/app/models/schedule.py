from sqlalchemy import Column, Integer, DateTime, ForeignKey
from sqlalchemy.orm import relationship

from app.db.base_class import Base


class Schedule(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    start = Column(DateTime, index=True)
    end = Column(DateTime, index=True)
    driver_id = Column(Integer, ForeignKey('driver.id'))
    bus_id = Column(Integer, ForeignKey('bus.id'))

    driver = relationship("Driver", backref="Schedule")
    bus = relationship("Bus", backref="Schedule")
