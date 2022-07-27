import logging
import coloredlogs

import yaml
from flask import Flask

from src.db_model.db_models import db
from sqlalchemy import create_engine

log = logging.getLogger(__name__)


coloredlogs.install(level='DEBUG')
coloredlogs.install(level='DEBUG', logger=log)


def create_app():
    app = Flask(__name__, instance_relative_config=True)

    with open('volume/config/app.yml', mode='r') as f:
        app_config: dict = yaml.load(f, Loader=yaml.Loader)

    app.config.from_mapping(app_config)
    app.url_map.strict_slashes = False

    log.critical(f'using {app_config["SQLALCHEMY_DATABASE_URI"]}')

    # engine = create_engine(url=app_config['SQLALCHEMY_DATABASE_URI'], echo=True)
    # cnx = engine.connect()

    db.app = app
    db.init_app(app=app)

    db.create_all()

    @app.get('/test')
    def test():
        return {'test': 'good'}, 200

    return app
