from sqlalchemy import Column, Integer, String
from db.base_class import Base


class Bus(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    capacity = Column(String, index=True)
    model = Column(String, index=True)
    make = Column(String, index=True)


