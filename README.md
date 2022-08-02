# SCHEDULE MANAGEMENT APP

## Summary
This solution attempts to solve the `advanced` challenge using a `flask` API and a `mysql` database.

The system can be run fully containerized using the `docker-compose` scripts or on a python venv using:
```shell
$ pip3 install virtualenv                           # installation
$ python3 -m virtualenv venv                        # init venv
$ source ~/<PATH>/api_project/venv/bin/activate     # activate
$ pip3 install -r requirements.txt                  # install requirements for venv
```
* User might need to specify python interpreter if the IDE has not selected the venv python interpreter automatically...

## Docker compose solutions
| file | functionality |
| --- | --- |
| `docker-compose.db.yml` | for running a mysql contianer accessible by: `tester:password@0.0.0.0:3306/Schedules` |
| `volume/config/flask_test_docker.yaml` | for running mysql, API containers and giving the pytest results defined in `./tests/`; it accesses the containerized data at `tester:password@mysql_db:3306/Schedules` |
| `docker-compose.yml` | for running mysql, API containers on the same docker bridge network where db can be accessed by: `tester:password@mysql_db:3306/Schedules` |

## Testing API requests
> Given that the APP (both containers) is runnning, user may test API calls either:
1. Using vscode addon: [__REST CLIENT__](https://marketplace.visualstudio.com/items?), user may use the `./test.http` file which contains all the possible requrests. User may edit: body, queries and path for every request if necessary.
2. Using swagger endpoint: `0.0.0.0:5000/apidocs`, where a `swagger.io UI` is provided.

## Running the APP
> using `docker-compose.yml`:
```
$ docker-compose -f docker-compose.yml up
```
* The database is instantiated by not populated. (more on populating the database in a second)
* Database can be populated using the following request which user may send using the `./tests.http` file or `swagger.io UI`
```text
POST http://{{socket}}/{{prefix}}/population
    ?buses=250
    &drivers=1000
    ?from=2022-01-01
    &to=2022-12-01
```
* The above request would insert ~1M schedules in the `Schedule` table, and ~200k in the `Available_Schedule` table.
* User may retreive available schedules via:
```
GET http://{{socket}}/{{prefix}}/available_schedule
```

## Testing
> using `pytest`, either:
1. Using `docker-compose.pytest.yml` where pytest logs may be observed from docker logs
2. Using `docker-compose.db.yml` alongside:
```shell
$ docker-compose -f docker-compose.yml up -d
$ python -m pytest -v -s
```
if for some reason, all tests fail, try removing the database volume with:
```shell
$ rm -rf volume/db_data/
```
and restarting the test

## Missing
1. securing the API
2. adding role based access control
1. adding service hooks (email notifications)

## TODOs
1. Figuring out a way to import schemas into swagger yaml files (I tried for hours follwing [swagger.io Using $ref](https://swagger.io/docs/specification/using-ref/) but could not...)
2. Figuring out how to aggregate yaml files together in a way that is more organized that in `/src/docs` (I tried for hours follwing [swagger.io Paths and Operations](https://swagger.io/docs/specification/paths-and-operations/) but could not...)
3. Verifying request body keys using `swagger validate` but I did not allocate time for it and did not want to extend my duedate

## Fulfillment of the challenge requirements
1. The application should allow a user to manage the schedule and add new entries for buses, drivers and assign shifts to drivers.
```
POST http://{{socket}}/{{prefix}}/bus
POST http://{{socket}}/{{prefix}}/driver
POST http://{{socket}}/{{prefix}}/schedule
```
* where request body samples can be found in `./test.http`

2. The application should allow a user to retrieve the schedule for every driver for a given week.
```
GET http://{{socket}}/{{prefix}}/schedule
    ?from=2022-01-01 00:00
    &to=2022-01-08 00:00
    &driver_id=1
```
3. The application should allow a user to retrieve the schedule for every bus for a given week.
```
GET http://{{socket}}/{{prefix}}/schedule/by_bus
    ?from=2022-01-01 00:00
    &to=2022-03-03 00:00
```
4. The application should allow a user to retrieve top X drivers with more shedules per week, during a number of consecutive weeks (from ... to)
```
GET http://{{socket}}/{{prefix}}/driver/top/10
    ?from=2022-01-02 00:00
    &to=2022-03-16 00:00
```

## My Notes
* Swagger API documentation swallowed most of my time... There must be a more efficient way to utilze swagger I am sure.
* The yaml summary in swagger yaml in `/src/docs/` does not display next to API endpoints on the `swagger.io UI` for some reason...
