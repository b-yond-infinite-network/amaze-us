import unittest
import requests


class CargoServiceTests(unittest.TestCase):
    def test_add_and_delete_cargo(self):
        url = "http://cargo-app:8080/api/cargo"

        payload = "{\"text\":\"User1\"}"
        headers = {
            'Accept': 'application/json, text/plain, */*',
            'Accept-Language': 'en-GB,en-US;q=0.9,en;q=0.8',
            'Connection': 'keep-alive',
            'Content-Type': 'application/json;charset=UTF-8',
            'Origin': 'http://0.0.0.0:8080',
            'Referer': 'http://0.0.0.0:8080/',
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36'
        }

        response = requests.request("POST", url, headers=headers, data=payload)

        self.assertEqual(response.json()[0]['text'], 'User1')

        # Delete test
        cargoId = response.json()[0]['_id']
        url = "http://cargo-app:8080/api/cargo/" + cargoId

        headers = {
            'Accept': 'application/json, text/plain, */*',
            'Accept-Language': 'en-GB,en-US;q=0.9,en;q=0.8',
        }
        response = requests.request("DELETE", url, headers=headers, data=payload)

        self.assertEqual(0, len(response.json()))


if __name__ == '__main__':
    unittest.main()
