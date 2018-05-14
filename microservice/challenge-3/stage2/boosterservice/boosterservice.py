from urllib.request import Request, urlopen
from urllib.error import URLError

class _BoosterService:
    boosterUrl = ''
    def connect(self, boosterUrl):
        self.boosterUrl += boosterUrl

    def checkFuel(self):
        try:
            request = Request(self.boosterUrl)
            response = urlopen(request)
            booster = response.read()
            return booster
        except URLError as e:
            return  ('Booster still has fuel, not released', str(e.reason))
# There can be only one.
booster = _BoosterService()