from requests.exceptions import ConnectionError

from booster import BoosterService
from config.env import BOOSTER_URL, TANKS


def main():
    try:
        service = BoosterService(BOOSTER_URL, TANKS)
        booster_stage_finished = service.booster_stage_finished()

        if booster_stage_finished:
            print('No more fuel, released')
        else:
            print('Booster still has fuel, not released')

    except ConnectionError as e:
        print('Connection Error - Assuming booster still has fuel, not released\n', e)


if __name__ == '__main__':
    main()
