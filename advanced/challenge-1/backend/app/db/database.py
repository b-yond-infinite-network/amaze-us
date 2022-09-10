from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker

engine = create_engine(
    'sqlite+pysqlite:///:memory:?check_same_thread=true', echo=True, future=True)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()
