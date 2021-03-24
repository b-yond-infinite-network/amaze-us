import requests


class BoosterService:
    def __init__(self, booster_url, tanks):
        self.booster_url = booster_url
        self.tanks = tanks

    def booster_stage_finished(self):
        for tank in self.tanks:
            response = requests.get('{}/tanks/{}/fuel'.format(self.booster_url, tank))

            if response.status_code != 200 or len([f for f in response.json() if not f['done']]) > 0:
                return False

        return True
