from dotenv import load_dotenv
from urllib.request import Request, urlopen, URLError
import os

load_dotenv()

request = Request('{}/tanks'.format(os.getenv('BOOSTER_URL')))

try:
    response = urlopen(request)
    booster = response.read()
    print(booster)

except URLError as e:
    print('Booster still has fuel, not released', e)

