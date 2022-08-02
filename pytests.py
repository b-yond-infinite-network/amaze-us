import logging

import pytest
import yaml
import sys
from sqlalchemy import create_engine
from time import sleep


log = logging.getLogger(__name__)


def run_pytests():
    with open('tests/flask.yaml', mode='r') as f:
        app_config: dict = yaml.load(f, Loader=yaml.Loader)
        conn_string = app_config['SQLALCHEMY_DATABASE_URI']

    ''' even though app container 'depends' on db container,
        mySQL container may take a couple of seconds in case the container does
        not detect a shared db volume (eg: when it runs for the first time)
        ---
        this loop ensures that the app container starts running the tests only when db socket is open.
    '''
    retry_time = 5
    while True:
        try:
            conn = create_engine(conn_string)
            with conn.begin(): pass
        except KeyboardInterrupt:
            return 0
        except Exception as err:
            log.error(f'on starting app: {err}')
            log.error(f'retrying in {retry_time}s ...')
            sleep(5)
        else:
            sys.exit(pytest.main(['-s', '-v']))


if __name__ == '__main__':
    exit(run_pytests())
