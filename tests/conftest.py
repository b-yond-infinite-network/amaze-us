'''
fixtures for tests...
# ? source: https://testdriven.io/blog/flask-pytest/
'''

import pytest
from src import create_app, db


@pytest.fixture(scope='module')
def test_client():
    flask_app = create_app('volume/config/flask_test.yml')

    # Create a test client using the Flask application configured for testing
    with flask_app.test_client() as testing_client:
        # Establish an application context
        with flask_app.app_context():
            yield testing_client


@pytest.fixture(scope='module')
def init_database(test_client):
    db.create_all()

    # * POPULATE
    db.session.commit()
    yield

    db.drop_all()
