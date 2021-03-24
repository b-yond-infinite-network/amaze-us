import os

BOOSTER_URL = os.getenv('BOOSTER_URL')
TANKS = map(lambda t: t.strip(), os.getenv('TANKS').split(','))
