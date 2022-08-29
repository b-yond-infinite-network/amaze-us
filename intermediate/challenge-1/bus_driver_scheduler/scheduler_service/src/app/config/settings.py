from starlette.config import Config

"""
File used for centralizing the application config. It reads sensitive information from .env files.
"""

config = Config(".env-scheduler-dev")

# APPLICATION
ENTRYPOINT = "main:app"
VERSION = "1.0.0"

# OpenAPI Documentation
TITLE = "Scheduler service"
DESCRIPTION = "Service focused on managing the scheduler resource."
DOCS_PATH = "/"

# Server (Uvicorn)
HOST = "127.0.0.1"
PORT = 8082
RELOAD = True

# Database
CONN = config("CONN", default=None)
SCHEMA = config("SCHEMA", default="scheduler")
DEBUG = False

# CORS
ALLOW_ORIGINS = ["http://localhost", "http://localhost:3000"]
ALLOW_METHODS = ["POST", "GET", "PUT", "DELETE"]
ALLOW_HEADERS = ["*"]

# Services
DRIVER_SERVICE_URL = config("DRIVER_SERVICE_URL", default="http://driver:8081/driver")
BUS_SERVICE_URL = config("BUS_SERVICE_URL", default="http://bus:8080/bus")
