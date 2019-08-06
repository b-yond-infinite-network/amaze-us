# App42Pass-Java-MySql-SampleApp

In this project the monolithic app is broken down into 3 fully scalable components: 

- **my-test-app**(sorry about the name as I started this off as a trial app): an angular 6 based UI app for displaying users and adding new users.

- **user-service:** spring boot based microservice with capability of addition of retrieval of users.

- **database:** MySql image is used along with a simple start up script that creates user table and inserts few users.

Each project has its own Dockerfile which are used to build images for corresponding apps. Build images are pushed into Docker Hub so that application could be deployed anywhere. I have used docker swarm as the orchestrator that uses docker-compose.yml included in the project.

## Tech stack:

- Java 1.8
- Spring boot 2
- Angular 6
- MySql
- Docker

## Installation:

- install docker on your machine.

- run command : docker swarm init : This will start docker in swarm mode with current node as swarm manager.

- run command : docker stack deploy -c docker-compose.yml full-stack-user-app  : this will pull images and start containers. User-service app is scaled at a factor of 2, so total 4 containers get started, 1 for UI, 2 for middleware and 1 for database.

- Hit the following URL in browser : http://docker-machine-ip:4200/ and the application is running.
