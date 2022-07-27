import logging

import validators
from flask import Blueprint, request, jsonify
from datetime import datetime

from src.common import DT_FMT

from src.db_model.db_models import Schedule, Driver, Bus, db
from src.constants.http_status_codes import (
    HTTP_200_OK,
    HTTP_201_CREATED,
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_409_CONFLICT
)


log = logging.getLogger(__name__)


class Schedules():
    schedule_bp = Blueprint('schedule', __name__, url_prefix='/api/v1/schedule')

    @schedule_bp.get('/<int:id>')
    def get(id: int):
        schedule = Schedule.query.filter_by(id=id).first()

        if not schedule:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(schedule.as_dict()), HTTP_200_OK

    @schedule_bp.post('')
    def post():
        body = request.json

        driver = Driver.query.filter_by(email=body['driver_id']).first()
        bus = Bus.query.filter_by(email=body['bus_id']).first()

        if driver is None:
            return jsonify({'error': 'No driver with such id ...'}), HTTP_400_BAD_REQUEST

        if bus is None:
            return jsonify({'error': 'No bus with such id ...'}), HTTP_400_BAD_REQUEST

        if not Schedule.bus_is_free_at(bus.id, body['dt_start'], body['dt_end']):
            return jsonify({'error': 'Bus is occupied at the designated time slot ...'}), HTTP_409_CONFLICT

        if not Schedule.driver_is_free_at(driver.id, body['dt_start'], body['dt_end']):
            return jsonify({'error': 'driver already has a shift at the designated time slot ...'}), HTTP_409_CONFLICT

        schedule = Schedule(
            driver_id=body['driver_id'],
            bus_id=body['bus_id'],
            dt_start=datetime.strptime(body['dt_start'], DT_FMT),
            dt_end=datetime.strptime(body['dt_end'], DT_FMT)
        )
        db.session.add(schedule)
        db.session.commit()

        return schedule.as_dict(), HTTP_201_CREATED


class Drivers():
    driver_bp = Blueprint('driver', __name__, url_prefix='/api/v1/driver')

    @driver_bp.get('/<int:id>')
    def get(id: int):
        driver = Driver.query.filter_by(id=id).first()

        if not driver:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(driver.as_dict()), HTTP_200_OK

    @driver_bp.post('')
    def post():
        body = request.json

        if not body['first_name'].isalpha() or ' ' in body['first_name']:
            return jsonify({'error': 'first name contains bad characters ...'}), HTTP_400_BAD_REQUEST

        if not body['last_name'].isalpha() or ' ' in body['last_name']:
            return jsonify({'error': 'last name contains bad characters ...'}), HTTP_400_BAD_REQUEST

        if not validators.email(body['email']):
            return jsonify({'error': 'Email is invalid ...'}), HTTP_400_BAD_REQUEST

        if Driver.query.filter_by(email=body['email']).first() is not None:
            return jsonify({'error': 'Email is taken ...'}), HTTP_409_CONFLICT

        if Driver.query.filter_by(social_security_number=body['social_security_number']).first() is not None:
            return jsonify({'error': 'SSN is taken ...'}), HTTP_409_CONFLICT

        driver = Driver(
            first_name=body['first_name'],
            last_name=body['last_name'],
            email=body['email'],
            social_security_number=body['social_security_number']
        )
        db.session.add(driver)
        db.session.commit()

        return driver.as_dict(), HTTP_201_CREATED


class Buses():
    bus_bp = Blueprint('bus', __name__, url_prefix='/api/v1/bus')

    @bus_bp.get('/<int:id>')
    def get(id: int):
        bus = Bus.query.filter_by(id=id).first()

        if not bus:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(bus.as_dict()), HTTP_200_OK

    @bus_bp.post('')
    def post():
        body = request.json

        bus = Bus(
            model=body['model'],
            make=body['make']
        )
        db.session.add(bus)
        db.session.commit()

        return bus.as_dict(), HTTP_201_CREATED


schedule_bp = Schedules.schedule_bp
bus_bp = Buses.bus_bp
driver_bp = Drivers.driver_bp
