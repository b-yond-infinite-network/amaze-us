import logging
import os


logging_context = os.environ.get('KAFKA_TOPIC') # name log file same as kafka topic.
logging_context='evilnet'

logging.basicConfig(
    level=logging.INFO,
    format="{asctime} - {levelname:<8} # {message}",
    style='{',
    filename='{}.log'.format(logging_context),
    filemode='a',
    )

logger = logging.getLogger('{}-logging'.format(logging_context))



 



 





