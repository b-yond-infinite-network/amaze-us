# Colony Kepler API

![Backend CI](https://github.com/sylhare/SpaceStack/workflows/Backend%20CI/badge.svg)

## Setup

It is using JDK11 (openjdk version "11.0.2") as well as gradle 6.3.
Fully developed in Kotlin.
The persistent storage used is Mongo DB.

### Build

To build the project:

```bash
./gradlew clean build
```

### Run tests

Run the tests with coverage report:

```bash
./gradlew clean test jacocoTestReport
```

### With docker

You can create the docker image using:

```bash
./gradlew clean build dockerBuild
```

Then you can run it using standalone:

```bash
docker run -p 8103:8103 kepler-colony:latest 
```

It will run on [localhost:8103](http://localhost:8103).
To get the full view of the app with the db, use the docker compose file.

## Api Info

This api manage the Colony population via the approval or denial of baby requests.

- POST `/v1/login`: To login with your username / password and get the json web token (jwt).
- GET `/v1/population`: To get the number of people in the colony
- POST `/v1/baby/request`: To send a request to have a Baby.
- PUT `/v1/baby/request/{id}`: To approve or deny a request to have a baby via its id.
- GET `/v1/baby/request`: To get the list of pending requests.
- GET `/v1/baby/request/audit`: To get the list of approved requests from newest to oldest.

You can start the app and check the swagger at [localhost:8103/swagger-ui.html](http://localhost:8103/swagger-ui.html#/colony-controller)

You can even try the app API from there.
On the operation side, you can have access to the app auto generated metrics by reaching [localhost:8103/metrics](http://localhost:9100/actuator/metrics).
You can have the health of the app by reaching [localhost:8103/health](http://localhost:8103/health).