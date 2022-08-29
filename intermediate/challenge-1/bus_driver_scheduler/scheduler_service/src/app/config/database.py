from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base
from app.config import settings

"""
Configure SQLAlchemy engine and session using values from settings.py.
"""
ENGINE = create_engine(settings.CONN, echo=settings.DEBUG)
SESSION = sessionmaker(bind=ENGINE)
Base = declarative_base()
Base.metadata.schema = settings.SCHEMA


def get_db_session():
    try:
        db = SESSION()
        yield db
    finally:
        db.close()
