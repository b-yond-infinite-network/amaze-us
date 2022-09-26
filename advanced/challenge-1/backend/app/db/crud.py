from datetime import datetime
import logging

from sqlalchemy.orm import Session
from sqlalchemy.sql import func

from . import models, schemas


logger = logging.getLogger(__name__)


def get_user(db: Session, user_id: int):
    return db.query(models.User).filter(models.User.id == user_id).first()


def get_user_by_email(db: Session, email: str):
    return db.query(models.User).filter(models.User.email == email).first()


def get_drivers(db: Session):
    return db.query(models.Driver).all()


def get_top_drivers(db: Session, start: datetime, end: datetime, n: int):
    result = (
        db.query(
            models.Driver.first_name,
            models.Driver.last_name,
            func.count(models.Schedule.driver_id).label('total_tasks'),
            func.sum(models.Schedule.distance).label('total_distance')
        )
        .filter(models.Driver.id == models.Schedule.driver_id)
        .filter(models.Schedule.timestamp > start)
        .filter(models.Schedule.timestamp < end)
        .group_by(models.Schedule.driver_id)
        .order_by(func.sum(models.Schedule.distance).desc())
        .limit(n)
    )
    return result


def get_buses(db: Session):
    return db.query(models.Bus).all()


def create_driver(db: Session, driver: schemas.DriverCreate):
    logger.info(f'Creating driver {driver}')
    db_item = models.Driver(**driver.dict())
    db.add(db_item)
    db.commit()
    db.refresh(db_item)
    return db_item
