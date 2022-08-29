from starlette.config import Config

"""
File used for centralizing the application config. It reads sensitive information from .env files.
"""

config = Config(".env-bus-dev")

# APPLICATION
ENTRYPOINT = "main:app"
VERSION = "1.0.0"

# OpenAPI Documentation
TITLE = "Bus service"
DESCRIPTION = "Service focused on managing the bus resource."
DOCS_PATH = "/"

# Server (Uvicorn)
HOST = "127.0.0.1"
PORT = 8080
RELOAD = True

# Database
CONN = config("CONN", default=None)
SCHEMA = config("SCHEMA", default="bus")
DEBUG = False

# Logging
LOG_LEVEL = config("LOG_LEVEL", default="INFO")  # Valid values: INFO, DEBUG, WARNING
MESSAGE_FORMAT = f"%(asctime)s - %(name)s - %(levelname)s - %(message)s"
DATE_FORMAT = "%Y-%m-%d %H:%M"

# CORS
ALLOW_ORIGINS = ["http://localhost", "http://localhost:3000"]
ALLOW_METHODS = ["POST", "GET", "PUT", "DELETE"]
ALLOW_HEADERS = ["*"]
