from sqlalchemy import Column, Integer, String

from app.db.base_class import Base


class Driver(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    first_name = Column(String, index=True)
    last_name = Column(String, index=True)
    email = Column(String)
