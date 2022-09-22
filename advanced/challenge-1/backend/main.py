import logging

import coloredlogs
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.router import router
from app.db.initialize import initialize_db
from app.db.database import Base, engine, get_conn


logging.config.fileConfig('logging.conf', disable_existing_loggers=False)
coloredlogs.install(
    fmt='%(asctime)s,%(msecs)03d %(levelname)-8s %(name)s:l%(lineno)-4d %(message)s')

Base.metadata.create_all(bind=engine)
# I chose not to use alembic as I would not need any more migrations
initialize_db(get_conn())

app = FastAPI()

app.include_router(router)

origins = ['http://localhost:8080']
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=['*'],
    allow_headers=['*'],
    expose_headers=['*']
)
