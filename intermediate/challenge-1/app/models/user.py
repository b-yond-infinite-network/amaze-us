from sqlalchemy import Boolean, Column, Integer, String

from db.base_class import Base


class User(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    full_name = Column(String)
    email = Column(String, unique=True, nullable=False)
    hashed_password = Column(String, nullable=False)
    is_manager = Column(Boolean(), default=False)
