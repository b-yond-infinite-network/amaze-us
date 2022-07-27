import yaml
from flask import Flask


def create_app():
    app = Flask(__name__, instance_relative_config=True)

    with open('volume/config/app.yml', mode='r') as f:
        app_config: dict = yaml.load(f, Loader=yaml.Loader)

    app.config.from_mapping(app_config)
    app.url_map.strict_slashes = False

    @app.get('/test')
    def test():
        return {'test': 'good'}, 200

    return app
