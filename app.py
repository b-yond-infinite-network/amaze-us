import argparse

from src import create_app


parser = argparse.ArgumentParser()
parser.add_argument('--config', '-c',
                    nargs='?', type=str, default='volume/config/flask.yml',
                    help='flask config. Default: %(default)s'
                    )
parser.add_argument('--host',
                    nargs='?', type=str, default='0.0.0.0',
                    help='flask host. Default: %(default)s'
                    )
parser.add_argument('--port',
                    nargs='?', type=str, default=5000,
                    help='flask port. Default: %(default)s'
                    )
parser.add_argument('--debug',
                    nargs='?', type=str, default=False,
                    help='flask debug mode. Default: %(default)s'
                    )
ARGS = parser.parse_args()


app = create_app(ARGS.config)
app.run(host=ARGS.host, port=ARGS.port, debug=ARGS.debug)
