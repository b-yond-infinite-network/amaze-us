from sqlalchemy import Column, ForeignKey, Integer, Date, Time
from sqlalchemy.orm import relationship, backref
from db.base_class import Base


class Schedule(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    date = Column(Date, index=True)
    start = Column(Time, index=True)
    end = Column(Time, index=True)
    bus_id = Column(Integer, ForeignKey('bus.id'))
    driver_id = Column(Integer, ForeignKey('driver.id'))

    bus = relationship("Bus", backref=backref("Schedule", cascade="all, delete-orphan"))
    driver = relationship("Driver", backref=backref("Schedule", cascade="all, delete-orphan"))
