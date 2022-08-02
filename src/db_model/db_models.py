from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import or_, and_
from datetime import datetime

from src.common import DT_FMT, MAX_EMAIL_LEN, MAX_NAME_LEN

db = SQLAlchemy()


class Bus(db.Model):
    ''' model for bus
    '''
    id = db.Column(db.Integer, primary_key=True)
    model = db.Column(db.String(MAX_NAME_LEN), nullable=False)
    make = db.Column(db.String(MAX_NAME_LEN), nullable=False)
    shifts = db.relationship('Schedule', backref='bus', lazy='joined')

    def __repr__(self) -> str:
        return f'{self.make}:{self.model}'

    def __str__(self): return repr(self)

    def as_dict(self):
        return {
            'id': self.id,
            'model': self.model,
            'make': self.make,
        }


class Driver(db.Model):
    ''' model for driver
    '''
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(MAX_NAME_LEN), nullable=False)
    last_name = db.Column(db.String(MAX_NAME_LEN), nullable=False)
    email = db.Column(db.String(MAX_EMAIL_LEN), unique=True, nullable=False)
    social_security_number = db.Column(db.Integer, unique=True, nullable=False)
    shifts = db.relationship('Schedule', backref='driver', lazy='joined')

    def __repr__(self) -> str:
        return f'{self.first_name} {self.last_name}'

    def __str__(self): return repr(self)

    def as_dict(self):
        return {
            'id': self.id,
            'first_name': self.first_name,
            'last_name': self.last_name,
            'email': self.email,
        }


class Schedule(db.Model):
    ''' model for schedule
    '''
    id = db.Column(db.Integer, primary_key=True)
    driver_id = db.Column(db.Integer, db.ForeignKey('driver.id'))
    bus_id = db.Column(db.Integer, db.ForeignKey('bus.id'))
    dt_start = db.Column(db.DateTime, nullable=False)
    dt_end = db.Column(db.DateTime, nullable=False)

    # attempted inheritance but there's conflict in mro...
    __mapper_args__ = {
        'polymorphic_identity': 'schedule',
    }

    def __repr__(self) -> str:
        return f'D:{self.driver_id} B:{self.bus_id} {self.dt_start} - {self.dt_end}'

    def __str__(self): return repr(self)

    def as_dict(self):
        return {
            'id': self.id,
            'driver_id': self.driver_id,
            'bus_id': self.bus_id,
            'dt_start': datetime.strftime(self.dt_start, DT_FMT),
            'dt_end': datetime.strftime(self.dt_end, DT_FMT)
        }

    @staticmethod
    def get_overlapping_scheds(
        driver_id: int, bus_id: int, inc_dt_start: datetime, inc_dt_end: datetime
    ) -> dict:
        results = Schedule.query.filter(
            or_(Schedule.bus_id == bus_id, Schedule.driver_id == driver_id),
            or_(
                and_(Schedule.dt_start <= inc_dt_start, inc_dt_start <= Schedule.dt_end),
                and_(Schedule.dt_start <= inc_dt_end, inc_dt_end <= Schedule.dt_end)
            )
        ).limit(10).all()
        return results


class AvaiableSchedule(db.Model):
    ''' model for schedule
    '''
    id = db.Column(db.Integer, primary_key=True)
    driver_id = db.Column(db.Integer, db.ForeignKey('driver.id'))
    bus_id = db.Column(db.Integer, db.ForeignKey('bus.id'))
    dt_start = db.Column(db.DateTime, nullable=False)
    dt_end = db.Column(db.DateTime, nullable=False)

    # attempted inheritance but there's conflict in mro...
    __mapper_args__ = {
        'polymorphic_identity': 'schedule',
    }

    def __repr__(self) -> str:
        return f'D:{self.driver_id} B:{self.bus_id} {self.dt_start} - {self.dt_end}'

    def __str__(self): return repr(self)

    def as_dict(self):
        return {
            'id': self.id,
            'driver_id': self.driver_id,
            'bus_id': self.bus_id,
            'dt_start': datetime.strftime(self.dt_start, DT_FMT),
            'dt_end': datetime.strftime(self.dt_end, DT_FMT)
        }
