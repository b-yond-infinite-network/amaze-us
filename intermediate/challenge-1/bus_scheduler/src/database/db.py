import logging
import os
from typing import Generator

from fastapi import Depends
from sqlalchemy import create_engine, event, insert
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

from src.config import settings

from . import seed
from .db_error_handler import handler

engine = create_engine(
    url=f"{settings.db.dialect}{os.path.abspath(os.curdir)}/{settings.db.name}",
    echo=settings.db.echo,
    connect_args=settings.db.connect_args,
)

event.listen(
    engine, "connect", lambda c, _: c.execute("pragma foreign_keys=on")
)
event.listen(engine, "handle_error", handler)


def get_session() -> Generator:
    db = sessionmaker(autocommit=False, autoflush=False, bind=engine)()
    try:
        yield db
    finally:
        db.close()


Base = declarative_base()
ActiveSession = Depends(get_session)

logger = logging.getLogger(__name__)


class StartDB:
    def __init__(self, seed: bool = False):
        self.db = next(get_session())
        self.seed = seed
        self.__init_db()

    def __init_db(self) -> None:
        self.__create_db_and_tables()
        if self.seed:
            self.__seed_db()

    @staticmethod
    def __create_db_and_tables():
        Base.metadata.create_all(bind=engine)

    def __seed_db(self):
        for model in Base.metadata.sorted_tables:
            model_name = model.fullname.upper()
            is_seeded = self.db.query(model).first()
            if is_seeded or not seed.__dict__.__contains__(model_name):
                continue  # pragma: no cover

            logger.info(f"Creating {model_name}...")
            for item in seed.__dict__.get(model_name):
                with engine.connect() as conn:
                    stmt = insert(model).values(item)
                    conn.execute(stmt)

            logger.info(f"{model_name} created successfully!")
