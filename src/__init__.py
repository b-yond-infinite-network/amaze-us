import logging
import coloredlogs

import yaml
from flask import Flask
from flasgger import Swagger

from src.db_model.db_models import db
from src.blueprints import schedule_bp, bus_bp, driver_bp, available_schedule_bp
from src.populate import population_bp
from src.constants.http_status_codes import HTTP_200_OK
from src.config.swagger import swagger_config, swagger_template

coloredlogs.install(level=logging.DEBUG)


log = logging.getLogger(__name__)


def create_app(conf: str) -> Flask:
    ''' create app, bind db, register blueprints, config swagger
    '''
    app = Flask(__name__, instance_relative_config=True)

    with open(conf, mode='r') as f:
        app_config: dict = yaml.load(f, Loader=yaml.Loader)

    app_config['SWAGGER'] = {
        'title': 'Schedules API',
        'uiversion': 3
    }
    log.debug(f'using {app_config["SQLALCHEMY_DATABASE_URI"]}')

    app.config.from_mapping(app_config)
    app.url_map.strict_slashes = False

    db.app = app
    db.init_app(app=app)
    db.create_all()

    app.register_blueprint(schedule_bp)
    app.register_blueprint(bus_bp)
    app.register_blueprint(driver_bp)
    app.register_blueprint(available_schedule_bp)
    app.register_blueprint(population_bp)

    Swagger(
        app,
        config=swagger_config, template=swagger_template
    )

    @app.get('/')
    def test(): return {'test': 'good'}, HTTP_200_OK

    return app
