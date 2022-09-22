import logging
from dotenv import dotenv_values
from pathlib import Path


logger = logging.getLogger(__file__)
path = str(Path(__file__).resolve().parents[1].absolute()) + '/.env'
logger.info(f'Loading env file from {path}')
settings = {
    **dotenv_values(path)
}
logger.debug(f'Found: {settings}')
