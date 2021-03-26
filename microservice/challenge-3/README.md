# Challenge 3 - It is rocket science!

Each subproject has instructions to be executed in its own readme file:
* [booster](./booster/README.md)
* [cargo](./cargo/README.md)
* [stage2](./stage2/README.md)
* [e2e tests](./e2e/README.md)

Anyway, to simplify testing the app, there is a docker compose file, that has the following:
* The booster app
* The MySQL instance needed for the booster
* The cargo app
* The MongoDB instance needed for the cargo
* The stage 2 app
    * This has a profile specified, so it doesn't run by default.

To run the docker compose:
```bash
docker-compose up
```

After everything started, you can run the booster with this command:
```bash
docker-compose run stage2
```
By default, it has set the `TANKS` variable to "Tank1,Tank2", if you want to check for other tanks, execute:
```bash
docker-compose run -e -e TANKS=tank33,tank54 stage2
```

**You can run this command to clean all the mess after testing it:**
```bash
docker-compose down --rmi local --volumes --remove-orphans
```

## Running E2E tests

To directly run the end-to-end tests, execute the following commands:
```bash
docker-compose up
cd e2e
nvm use
npm install
npm test
```
