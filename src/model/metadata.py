import databases
import os

import sqlalchemy

metadata = sqlalchemy.MetaData()
database = databases.Database(os.getenv('DB_PATH'))
