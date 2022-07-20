from sqlalchemy import Boolean, Column, Integer, String

from src.database.db import Base


class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    first_name = Column(String(256))
    last_name = Column(String(256))
    email = Column(String, index=True, nullable=False, unique=True)
    is_superuser = Column(Boolean, default=False)
    hashed_password = Column(String, nullable=False)
