import logging

from sqlalchemy.orm import Session

from . import models, schemas


logger = logging.getLogger(__name__)


def get_user(db: Session, user_id: int):
    return db.query(models.User).filter(models.User.id == user_id).first()


def get_user_by_email(db: Session, email: str):
    return db.query(models.User).filter(models.User.email == email).first()


def get_drivers(db: Session):
    return db.query(models.Driver).all()


def get_buses(db: Session):
    return db.query(models.Bus).all()


def create_driver(db: Session, driver: schemas.DriverCreate):
    logger.info(f'Creating driver {driver}')
    db_item = models.Driver(**driver.dict())
    db.add(db_item)
    db.commit()
    db.refresh(db_item)
    return db_item
