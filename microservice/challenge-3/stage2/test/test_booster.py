import unittest
from random import randint
import responses

from booster import BoosterService


class TestBoosterService(unittest.TestCase):
    def setUp(self):
        self.url = 'http://test.com'
        self.tanks = ['tank1', 'tank2']
        self.service = BoosterService(self.url, self.tanks)

        self.responses = responses.RequestsMock()
        self.responses.start()

        self.addCleanup(self.responses.stop)
        self.addCleanup(self.responses.reset)

    def test_booster_stage_finished_success_case(self):
        for tank in self.tanks:
            self.responses.add(
                responses.GET,
                '{}/tanks/{}/fuel'.format(self.url, tank),
                json=[{"done": True}],
                status=200
            )

        finished = self.service.booster_stage_finished()

        self.assertTrue(finished)

    def test_booster_stage_finished_response_not_200(self):
        for tank in self.tanks:
            self.responses.add(
                responses.GET,
                '{}/tanks/{}/fuel'.format(self.url, tank),
                json=[{"done": True}],
                status=randint(201, 599)
            )

        finished = self.service.booster_stage_finished()

        self.assertFalse(finished)

    def test_booster_stage_finished_response_not_all_done(self):
        for tank in self.tanks:
            self.responses.add(
                responses.GET,
                '{}/tanks/{}/fuel'.format(self.url, tank),
                json=[{"done": True}, {"done": False}],
                status=200
            )

        finished = self.service.booster_stage_finished()

        self.assertFalse(finished)


if __name__ == '__main__':
    unittest.main()
