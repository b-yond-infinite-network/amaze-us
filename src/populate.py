import logging

from flask import Blueprint, request, jsonify
from flasgger import swag_from

from src.populations import populate_buses, populate_drivers, populate_schedules, delete_all
from src.constants.http_status_codes import (
    HTTP_201_CREATED,
    HTTP_204_NO_CONTENT
)


log = logging.getLogger(__name__)


class Populations():
    population_bp = Blueprint('population', 'Population', url_prefix='/api/v1/population')

    @swag_from('docs/population_post.yaml')
    @population_bp.post('')
    def post():
        num_buses = request.args.get('buses', type=int, default=5)
        num_drivers = request.args.get('drivers', type=int, default=10)
        dt_from = request.args.get('from', type=str, default='2022-01-01')
        dt_to = request.args.get('to', type=str, default='2022-02-01')

        delete_all()

        log.info('populating Bus table ...')
        populate_buses(num_buses)

        log.info('populating Driver table ...')
        populate_drivers(num_drivers)

        log.info('populating Schedule & Available_Schedule tables ...')
        scheds, avaiable_scheds = populate_schedules(dt_start=dt_from, dt_end=dt_to)

        log.info('done')

        return jsonify({
            'created': scheds,
            'available': avaiable_scheds
        }), HTTP_201_CREATED

    @swag_from('docs/population_delete.yaml')
    @population_bp.delete('')
    def delete():
        delete_all()
        return jsonify({}), HTTP_204_NO_CONTENT


population_bp = Populations.population_bp
