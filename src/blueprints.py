import logging
from sqlalchemy import func, desc

import validators
from flask import Blueprint, request, jsonify
from datetime import datetime

from src.common import DT_FMT, PAGINATION_PER_PAGE
from src.db_model.db_models import Schedule, Driver, Bus, AvaiableSchedule, db
from src.constants.http_status_codes import (
    HTTP_200_OK,
    HTTP_201_CREATED,
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_409_CONFLICT
)


log = logging.getLogger(__name__)


def get_page_meta(pagination):
    return {
        'page': pagination.page,
        'pages': pagination.pages,
        'prev_page': pagination.prev_num,
        'next_page': pagination.next_num,
        'item_count': pagination.total,
    }


class Schedules():
    schedule_bp = Blueprint('schedule', __name__, url_prefix='/api/v1/schedule')

    @schedule_bp.get('/<int:id>')
    @schedule_bp.get('')
    def get(id: int = None):
        # * get schedule by id
        if id is not None:
            schedule = Schedule.query.filter_by(id=id).first()
            if not schedule:
                return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

            return jsonify(schedule.as_dict()), HTTP_200_OK

        # * get schedule driver_id, bus_id or/and week
        else:
            page = request.args.get('page', type=int, default=1)
            per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)

            dt_from = request.args.get('from', type=str, default=None)
            dt_to = request.args.get('to', type=str, default=None)

            driver_id = request.args.get('driver_id', type=int, default=None)
            bus_id = request.args.get('bus_id', type=int, default=None)

            dt_from = datetime.strptime(dt_from, DT_FMT)
            dt_to = datetime.strptime(dt_to, DT_FMT)

            conds = []
            if driver_id:
                conds.append(Schedule.driver_id == driver_id)
            if bus_id:
                conds.append(Schedule.bus_id == bus_id)

            if dt_from:
                conds.append(Schedule.dt_start >= dt_from)
            if dt_to:
                conds.append(Schedule.dt_end >= dt_to)

            scheds_paginated = Schedule.query.filter(*conds).paginate(page, per_page)

            return jsonify({
                'data': [av_sched.as_dict() for av_sched in scheds_paginated.items],
                'meta': get_page_meta(scheds_paginated)
            }), HTTP_200_OK

    @schedule_bp.post('')
    def post():
        body = request.json

        driver = Driver.query.filter_by(id=body['driver_id']).first()
        bus = Bus.query.filter_by(id=body['bus_id']).first()

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

    @driver_bp.get('/top/<int:n>')
    def get_top_n(n: int):
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)

        dt_from = request.args.get('from', type=str, default=None)
        dt_to = request.args.get('to', type=str, default=None)

        dt_from = datetime.strptime(dt_from, DT_FMT)
        dt_to = datetime.strptime(dt_to, DT_FMT)

        top_driver_ids = Schedule.query.with_entities(
            Schedule.driver_id, func.count(Schedule.driver_id).label('count')
        ).group_by(Schedule.driver_id).order_by(desc('count')).limit(5).all()

        top_driver_ids = [res[0] for res in top_driver_ids]
        drivers_paginated = Driver.query.filter(Driver.id.in_(top_driver_ids)).paginate(page, per_page)

        return jsonify({
            'data': [driver.as_dict() for driver in drivers_paginated.items],
            'meta': get_page_meta(drivers_paginated)
        }), HTTP_200_OK

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
        bus = Bus(model=body['model'], make=body['make'])
        db.session.add(bus)
        db.session.commit()

        return bus.as_dict(), HTTP_201_CREATED


class AvaiableSchedules():
    ''' For displaying possible schedules to be inserted by the user in `schedules`
    '''
    available_schedule_bp = Blueprint('available_schedule', __name__, url_prefix='/api/v1/available_schedule')

    @available_schedule_bp.get('')
    def get():
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)

        av_scheds_paginated = AvaiableSchedule.query.paginate(page, per_page)

        return jsonify({
            'data': [av_sched.as_dict() for av_sched in av_scheds_paginated.items],
            'meta': get_page_meta(av_scheds_paginated)
        }), HTTP_200_OK


schedule_bp = Schedules.schedule_bp
bus_bp = Buses.bus_bp
driver_bp = Drivers.driver_bp
available_schedule_bp = AvaiableSchedules.available_schedule_bp
