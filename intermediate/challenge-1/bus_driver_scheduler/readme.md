# Bus Driver Scheduler

The application is composed of 3 services:

1. Bus - Focused on managing the bus resource.
2. Driver - Focused on managing the driver resource.
3. Scheduler - Focused on managing the schedule resource.

## App functionalities

### Bus service
CRUD operations on bus resource - create, read, update and delete a bus.

### Driver service
CRUD operations on driver resource - create, read, update and delete a driver.

### Schedule service
CRUD operations on schedule resource - create, read, update and delete a schedule.

`The application should allow a user to manage the schedule and add new entries for buses, drivers and assign shifts to drivers.`
This is done when you create a new schedule on post /schedule/.

`The application should allow a user to retrieve the schedule for every driver for a given week.`
This is done on the endpoint /schedule/driver

`The application should allow a user to retrieve the schedule for every bus for a given week.`
This is done on the endpoint /schedule/bus

## Running the application

**Pre-requisite:** Have docker, docker-compose and python 3.9 installed.

### Docker-compose
To start the application run on terminal `docker-compose up`. 
The compose file will build and start 4 containers that are bus_service, driver_service, schedule_service and postgre database.

### Running the tests

Install the dependencies on requirements.txt on each service at service_folder/src/requirements.txt running `pip install -r requiments.txt`
To run the tests go the each service folder and run `pytest`:

- `busDriverScheduler\bus_service pytest` -> run bus tests
- `busDriverScheduler\driver_service pytest` -> run driver tests
- `busDriverScheduler\scheduler_service pytest` -> run scheduler tests

You can run the tests from the container running:

- `docker-compose -f .\docker-composer.yml run bus pytest`
- `docker-compose -f .\docker-composer.yml run driver pytest`
- `docker-compose -f .\docker-composer.yml run scheduler pytest` **PS: the scheduler test running from the container is failing. But works running locally, couldn't figure out.**

 

### App URLS

| Service   | Host      | Port   | Endpoint   | Documentation (OpenAPI / Swagger) |     
|-----------|-----------|--------|------------|-----------------------------------|
| Bus       | 127.0.0.1 | 8080   | /bus       | http://127.0.0.1:8080             |                             
| Driver    | 127.0.0.1 | 8081   | /driver    | http://127.0.0.1:8081             |
| Scheduler | 127.0.0.1 | 8082   | /scheduler | http://127.0.0.1:8082             |


### App configuration

You can configure the app on each service at src/app/configure/settings.py . 

## Credentials

Service credentials are declared on .env files inside each service folder. eg: ./bus_service/src/.env-bus-dev
The environment files are read by the docker-compose.yaml in the root folder.
The .env files were committed only as example. Since it can contain credentials is must not be added to the repository.


Bus service .env inside ./bus_service/src/.env-bus-dev example

```
CONN=postgresql+psycopg2://db_user:db_pwd@host:port/database
SCHEMA=bus
```
Driver service .env inside ./driver_service/src/.env-driver-dev example

```
CONN=postgresql+psycopg2://db_user:db_pwd@host:port/database
SCHEMA=driver
```

Schedule service .env inside ./schedule_service/src/.env-schedule-dev example

```
CONN=postgresql+psycopg2://admin:12345@db:5432/busDriverScheduler
SCHEMA=scheduler
DRIVER_SERVICE_URL=http://driver:8081/driver
BUS_SERVICE_URL=http://bus:8080/bus
```
