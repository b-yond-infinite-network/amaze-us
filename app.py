import logging
import argparse
from time import sleep

from src import create_app

log = logging.getLogger(__name__)


def run():
    parser = argparse.ArgumentParser()
    parser.add_argument('--config', '-c',
                        nargs='?', type=str, default='volume/config/flask.yaml',
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

    ''' even though app container 'depends' on db container,
        mySQL container may take a couple of seconds in case the container does
        not detect a shared db volume (eg: when it runs for the first time)
        ---
        this loop ensures that the app container starts running the API only when db socket is open.
    '''
    retry_time = 5
    while True:
        try:
            app = create_app(ARGS.config)
            app.run(host=ARGS.host, port=ARGS.port, debug=ARGS.debug)
        except KeyboardInterrupt:
            return 0
        except Exception as err:
            log.error(f'on starting app: {err}')
            log.error(f'retrying in {retry_time}s ...')
            sleep(5)
        else:
            return 0


if __name__ == '__main__':
    exit(run())
