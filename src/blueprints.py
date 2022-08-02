''' blueprints for database models
'''

import logging
import collections
from sqlalchemy import func, desc

from email_validator import validate_email
from flask import Blueprint, request, jsonify
from flasgger import swag_from
from datetime import datetime

from src.common import DT_FMT, DATE_FMT, PAGINATION_PER_PAGE, MAX_NAME_LEN, MAX_EMAIL_LEN, VERSION
from src.db_model.db_models import Schedule, Driver, Bus, AvaiableSchedule, db
from src.constants.http_status_codes import (
    HTTP_200_OK,
    HTTP_201_CREATED,
    HTTP_400_BAD_REQUEST,
    HTTP_404_NOT_FOUND,
    HTTP_409_CONFLICT
)

PREFIX = f'/api/{VERSION}'
log = logging.getLogger(__name__)


def get_page_meta(pagination) -> dict:
    ''' return metadata concerning a list of retrieved items
    '''
    return {
        'page': pagination.page,
        'pages': pagination.pages,
        'prev_page': pagination.prev_num,
        'next_page': pagination.next_num,
        'item_count': pagination.total,
    }


class Schedules():
    ''' wraps API calls concerning schedules
    '''
    schedule_bp = Blueprint('schedule', 'Schedules', url_prefix=f'{PREFIX}/schedule')

    @swag_from('docs/schedule_get.yaml')
    @schedule_bp.get('/<int:id>')
    def get(id: int = None):
        schedule = Schedule.query.filter_by(id=id).first()
        if not schedule:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(schedule.as_dict()), HTTP_200_OK

    @swag_from('docs/schedule_filter.yaml')
    @schedule_bp.get('')
    def filter():
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)
        per_page = min(PAGINATION_PER_PAGE, per_page)

        dt_from = request.args.get('from', type=str, default=None)
        dt_to = request.args.get('to', type=str, default=None)

        driver_id = request.args.get('driver_id', type=int, default=None)
        bus_id = request.args.get('bus_id', type=int, default=None)

        conds = []      # all conditions are optional; append if provided
        if driver_id:
            conds.append(Schedule.driver_id == driver_id)
        if bus_id:
            conds.append(Schedule.bus_id == bus_id)

        try:
            if dt_from:
                dt_from = datetime.strptime(dt_from, DT_FMT)
                conds.append(Schedule.dt_start >= dt_from)
            if dt_to:
                dt_to = datetime.strptime(dt_to, DT_FMT)
                conds.append(Schedule.dt_end <= dt_to)
        except ValueError as err:
            return jsonify({'error': str(err)}), HTTP_400_BAD_REQUEST
        except TypeError:
            return jsonify({'error': 'Start date / end date not supplied in query ...'}), HTTP_400_BAD_REQUEST

        scheds_paginated = Schedule.query.filter(*conds).paginate(page, per_page)

        return jsonify({
            'data': [av_sched.as_dict() for av_sched in scheds_paginated.items],
            'meta': get_page_meta(scheds_paginated)
        }), HTTP_200_OK

    @swag_from('docs/schedule_post.yaml')
    @schedule_bp.post('')
    def post():
        body = request.json

        try:
            dt_from = datetime.strptime(body['dt_start'], DT_FMT)
            dt_to = datetime.strptime(body['dt_end'], DT_FMT)
        except ValueError as err:
            return jsonify({'error': str(err)}), HTTP_400_BAD_REQUEST
        except KeyError as key:
            return jsonify({'error': f'{key} not supplied in request body ...'}), HTTP_400_BAD_REQUEST

        driver = Driver.query.filter_by(id=body['driver_id']).first()
        bus = Bus.query.filter_by(id=body['bus_id']).first()

        # * check if driver exists in database
        if driver is None:
            return jsonify({'error': 'No driver with such id ...'}), HTTP_400_BAD_REQUEST

        # * check if bus exists in database
        if bus is None:
            return jsonify({'error': 'No bus with such id ...'}), HTTP_400_BAD_REQUEST

        # * check for overlapping schedules
        conflicting_scheds = Schedule.get_overlapping_scheds(driver.id, bus.id, dt_from, dt_to)
        if conflicting_scheds:
            return jsonify({
                'error': 'bus/driver is occupied at the designated time slot ...',
                'conflicts': [sched.as_dict() for sched in conflicting_scheds]
            }), HTTP_409_CONFLICT

        schedule = Schedule(
            driver_id=body['driver_id'],
            bus_id=body['bus_id'],
            dt_start=dt_from,
            dt_end=dt_to
        )
        db.session.add(schedule)
        db.session.commit()

        return schedule.as_dict(), HTTP_201_CREATED

    @swag_from('docs/schedule_get_by_driver.yaml')
    @schedule_bp.get('/by_driver')
    def get_scheds_by_driver():
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)
        per_page = min(PAGINATION_PER_PAGE, per_page)

        dt_from = request.args.get('from', type=str, default=None)
        dt_to = request.args.get('to', type=str, default=None)

        try:
            dt_from = datetime.strptime(dt_from, DATE_FMT)
            dt_to = datetime.strptime(dt_to, DATE_FMT)
        except ValueError as err:
            return jsonify({'error': str(err)}), HTTP_400_BAD_REQUEST
        except KeyError as key:
            return jsonify({'error': f'{key} not supplied in request body ...'}), HTTP_400_BAD_REQUEST

        schedules = db.session.query(
            Schedule.driver_id, Schedule.bus_id,
            Schedule.dt_start, Schedule.dt_end
        ).filter(
            dt_from <= Schedule.dt_start,
            Schedule.dt_start <= dt_to
        ).paginate(page, per_page)

        # * group by driver_id
        driver_scheds = collections.defaultdict(dict)
        for driver_id, bus_id, dt_start, dt_end in schedules.items:
            if bus_id not in driver_scheds[driver_id]:
                driver_scheds[driver_id][bus_id] = []
            driver_scheds[driver_id][bus_id].append([
                datetime.strftime(dt_start, DT_FMT), datetime.strftime(dt_end, DT_FMT),
            ])

        return jsonify({
            'data': driver_scheds,
            'meta': get_page_meta(schedules)
        }), HTTP_200_OK

    @swag_from('docs/schedule_get_by_bus.yaml')
    @schedule_bp.get('/by_bus')
    def get_scheds_by_bus():
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)
        per_page = min(PAGINATION_PER_PAGE, per_page)

        dt_from = request.args.get('from', type=str, default=None)
        dt_to = request.args.get('to', type=str, default=None)

        try:
            dt_from = datetime.strptime(dt_from, DATE_FMT)
            dt_to = datetime.strptime(dt_to, DATE_FMT)
        except ValueError as err:
            return jsonify({'error': str(err)}), HTTP_400_BAD_REQUEST
        except KeyError as key:
            return jsonify({'error': f'{key} not supplied in request body ...'}), HTTP_400_BAD_REQUEST

        schedules = db.session.query(
            Schedule.driver_id, Schedule.bus_id,
            Schedule.dt_start, Schedule.dt_end
        ).filter(
            dt_from <= Schedule.dt_start,
            Schedule.dt_start <= dt_to
        ).paginate(page, per_page)

        # * group by bus_id
        bus_scheds = collections.defaultdict(dict)
        for driver_id, bus_id, dt_start, dt_end in schedules.items:
            if driver_id not in bus_scheds[bus_id]:
                bus_scheds[bus_id][driver_id] = []
            bus_scheds[bus_id][driver_id].append([
                datetime.strftime(dt_start, DT_FMT), datetime.strftime(dt_end, DT_FMT),
            ])

        return jsonify({
            'data': bus_scheds,
            'meta': get_page_meta(schedules)
        }), HTTP_200_OK


class Drivers():
    ''' wraps API calls concerning drivers
    '''
    driver_bp = Blueprint('driver', 'Drivers', url_prefix=f'{PREFIX}/driver')

    @swag_from('docs/driver_get.yaml')
    @driver_bp.get('/<int:id>')
    def get(id: int):
        driver = Driver.query.filter_by(id=id).first()
        if not driver:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(driver.as_dict()), HTTP_200_OK

    @swag_from('docs/driver_get_top_n.yaml')
    @driver_bp.get('/top/<int:n>')
    def get_top_n(n: int):
        dt_from = request.args.get('from', type=str, default=None)
        dt_to = request.args.get('to', type=str, default=None)
        try:
            dt_from = datetime.strptime(dt_from, DATE_FMT)
            dt_to = datetime.strptime(dt_to, DATE_FMT)
        except ValueError as err:
            return jsonify({'error': str(err)}), HTTP_400_BAD_REQUEST
        except TypeError:
            return jsonify({'error': 'Start date / end date not supplied in query ...'}), HTTP_400_BAD_REQUEST

        # * select from schedules table, grouping by driver id schedules
        group_counts = db.session.query(Schedule).with_entities(
            func.year(Schedule.dt_start).label('YEAR'),
            func.week(Schedule.dt_start).label('WEEK'),
            Schedule.driver_id,
            func.count(Schedule.driver_id).label('COUNT')
        ).filter(
            dt_from <= Schedule.dt_start,
            Schedule.dt_start <= dt_to
        ).group_by(
            'YEAR', 'WEEK', Schedule.driver_id
        ).order_by(
            desc('COUNT')
        ).all()

        # * group by week day until each weekday has N entries
        driver_scores = collections.defaultdict(dict)
        for year, week_number, driver_id, count in group_counts:
            day = f'{year}-W{week_number}'
            day = datetime.strftime(
                # ? week day to date source: https://stackoverflow.com/questions/17087314/get-date-from-week-number
                datetime.strptime(day + '-1', '%Y-W%W-%w'), DATE_FMT
            )
            if len(driver_scores[day]) == n: continue
            driver_scores[day][driver_id] = count

        return jsonify(driver_scores), HTTP_200_OK

    @swag_from('docs/driver_post.yaml')
    @driver_bp.post('')
    def post():
        body = request.json

        # * name check
        if not body['first_name'].isalpha() or ' ' in body['first_name']:
            return jsonify({'error': 'first name contains bad characters ...'}), HTTP_400_BAD_REQUEST

        if not body['last_name'].isalpha() or ' ' in body['last_name']:
            return jsonify({'error': 'last name contains bad characters ...'}), HTTP_400_BAD_REQUEST

        if len(body['first_name']) > MAX_NAME_LEN:
            return jsonify({'error': f'first name should not exceed {MAX_NAME_LEN} characters ...'}), HTTP_400_BAD_REQUEST

        if len(body['last_name']) > MAX_NAME_LEN:
            return jsonify({'error': f'last name should not exceed {MAX_NAME_LEN} characters ...'}), HTTP_400_BAD_REQUEST

        # * email check
        try:
            validate_email(body['email'])
        except Exception as err:
            return jsonify({'error': f'Email is invalid: {err}'}), HTTP_400_BAD_REQUEST

        if len(body['email']) > MAX_EMAIL_LEN:
            return jsonify({'error': f'email should not exceed {MAX_EMAIL_LEN} characters ...'}), HTTP_400_BAD_REQUEST

        # * email duplicates check
        if Driver.query.filter_by(email=body['email']).first() is not None:
            return jsonify({'error': 'Email is taken ...'}), HTTP_409_CONFLICT

        # * ssn duplicates check
        if Driver.query.filter_by(social_security_number=body['social_security_number']).first() is not None:
            return jsonify({'error': 'SSN is taken ...'}), HTTP_409_CONFLICT

        try:
            driver = Driver(
                first_name=body['first_name'],
                last_name=body['last_name'],
                email=body['email'].lower(),
                social_security_number=body['social_security_number']
            )
        except KeyError as key:
            return jsonify({'error': f'{key} not supplied in request body ...'}), HTTP_409_CONFLICT

        db.session.add(driver)
        db.session.commit()

        return driver.as_dict(), HTTP_201_CREATED


class Buses():
    ''' wraps API calls concerning buses
    '''
    bus_bp = Blueprint('bus', 'Buses', url_prefix=f'{PREFIX}/bus')

    @bus_bp.get('/<int:id>')
    @swag_from('docs/bus_get.yaml')
    def get(id: int):
        bus = Bus.query.filter_by(id=id).first()
        if not bus:
            return jsonify({'message': 'Item not found'}), HTTP_404_NOT_FOUND

        return jsonify(bus.as_dict()), HTTP_200_OK

    @swag_from('docs/bus_post.yaml')
    @bus_bp.post('')
    def post():
        body = request.json

        # * name lengths check
        if len(body['model']) > MAX_NAME_LEN:
            return jsonify({'error': 'model name too long ...'}), HTTP_400_BAD_REQUEST

        if len(body['make']) > MAX_NAME_LEN:
            return jsonify({'error': 'make name too long ...'}), HTTP_400_BAD_REQUEST

        try:
            bus = Bus(model=body['model'], make=body['make'])
        except KeyError as key:
            return jsonify({'error': f'{key} not supplied in request body ...'}), HTTP_409_CONFLICT

        # * no need to check before adding bus; nothing is unique here
        db.session.add(bus)
        db.session.commit()

        return bus.as_dict(), HTTP_201_CREATED


class AvailableSchedules():
    ''' wraps API calls concerning available schedules
        For displaying possible schedules to be inserted by the user in `schedules`
    '''
    available_schedule_bp = Blueprint('available_schedule', 'AvailableSchedules', url_prefix=f'{PREFIX}/available_schedule')

    @swag_from('docs/available_schedule_get.yaml')
    @available_schedule_bp.get('')
    def get():
        page = request.args.get('page', type=int, default=1)
        per_page = request.args.get('per_page', type=int, default=PAGINATION_PER_PAGE)
        per_page = min(PAGINATION_PER_PAGE, per_page)

        av_scheds_paginated = AvaiableSchedule.query.paginate(page, per_page)
        return jsonify({
            'data': [av_sched.as_dict() for av_sched in av_scheds_paginated.items],
            'meta': get_page_meta(av_scheds_paginated)
        }), HTTP_200_OK


schedule_bp = Schedules.schedule_bp
bus_bp = Buses.bus_bp
driver_bp = Drivers.driver_bp
available_schedule_bp = AvailableSchedules.available_schedule_bp
