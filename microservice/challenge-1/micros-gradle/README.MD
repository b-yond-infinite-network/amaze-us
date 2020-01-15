
## Usage

It uses Java 11, here's my java version

```
java -version
java version "11.0.3" 2019-04-16 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.3+12-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.3+12-LTS, mixed mode)
```

### Installing from source

To compile, please clone the project and use gradle. This is a multi module Gradle project. It uses spring boot

```
git clone URL
cd micros-gradle
gradle build
```

So chose to work on RetwisJ and split it up on serveral microservices

I created the following micros:

* Auth service, in charge in authenticating the user, it holds a MySQL database backend, it uses Spring Boot and a jar packaging
* Zuul server, the API gateway service that communicates with the UI and the rest of the services
* UI a migrated version of the frontend part of the original retwisJ project, migrated to Spring boot, it uses a war packaging
* Eureka service, services uses for service discovery among the rest of the services
* Common a shared lib dependency across projects 
* Social service, a service in change of handling the followers/following information on a postgres DB
* Tweet service, a service that stores Tweet and Mention information on a Cassandra database
* Fanout Service, a service that goes and puts Tweet ids in Redis keys
* Timeline Service, a service that reads the Redis instance to display the timeline for the user

Note: not all functionality was completed, this week was a very busy week and home and at work. Sorry for that
I still needed to add unit tests and integrations tests

Update: I have included a good test suite and Docker files

### Microservices boundaries

#### Auth service

* The Auth service has its own database to hold user data 
* The database has only one table
* It uses stateless session and token based authentication using jwt tokens, if called behind
    Zuul gateway it doesn't need any token
* In theory it can be scaled to ensure availability on the server level and DB level
* No other service has the the user information, just user IDs
* It doesn't call any other service but it calls Eureka

#### Social service
* The Social service has its own database to hold  the user's following and followers data  
* The database has only two tables and join is required.
* It needs a jwt token to be able to connect to it, the auth service is going to be called beforehand if called behind
 Zuul gateway it doesn't need any token
* In theory it can be scaled to ensure availability on the server level and DB level
* No other service has the following/followers data
* It doesn't call any other service, but it calls Eureka

#### Tweet service
* The tweet service has it's own nosql database to hold tweets/mentions information
* The database has only two tables 
* It needs a jwt token to be able to connect to it, the auth service is going to be called beforehand if called behind
Zuul gateway it doesn't need any token
* Since it uses Cassandra in theory it can be scaled more easily
* No other service has tweet and mentions information.
* This service calls Fanout service and Eureka

#### Fanout Service
* The fanout service has write access to the Redis instance that holds the timeline information, 
the Timeline service has read access to it
* The redis db hold many keys per user's timeline, but in general it's easy to query
* The redis instance could be scaled using Redis Cluster or Redis Sentinel.
* The timeline service also handles timeline information.
* This service calls Social service and Eureka

#### Timeline service
* The timeline service has read access to the redis instace inorder to populate timeline information.
* The redis db hold many keys per user's timeline, but in general it's easy to query
* The redis instance could be scaled using Redis Cluster or Redis Sentinel.
* The Fanout service also handles timeline information.
* This service calls Tweet service and Eureka

### Ui Service 
* The UI service has no access to any database, it only connects through Zuul to other services via REST
* In theory it can be scaled if you deploy several tomcat servers
* It connect to Social, Tweet, Timeline, Auth, all requests pass through Zuul. It also connects to Eureka.

### Zuul service
* Zuul service does not have any database
* In theory it can be scaled with more servers
* All HTTP requests from the outside need to pass through Zuul to filter them or allow them
* It connects to AUTH SERVICE

### Eureka
* Eureka does not use any external database
* In theory it can be scaled with more servers
* All services connect to Eureka


### how to RUN

You will have to run multiple terminals and first run the docker file to bring up databases (cassandra, mysql, postgres and redis)

`docker-compose up`

1. First startup Eureka server, in one terminal

```
cd eureka 
gradle bootRun

```

2. Then startup Zuul server, in one terminal

```
cd zuul 
gradle bootRun

```

3. Then startup the Auth server, in one terminal

```
cd auth 
gradle bootRun

```

4. The you can start these services in any order, in different terminals

```
cd social 
gradle bootRun

cd tweet
gradle bootRun

cd fanout
gradle bootRun

cd timeline
gradle bootRun
```

5. Last step is to run the UI server

```
cd ui
gradle bootRun
```


UI listens on port 3000, you can go and open up the browser and hit

`http://localhost:3000`

### Docker

In every project there is a Dockerfile and each project can use the docker Spring profile be configured using docker.
Two profiles were created for this project. docker and local... local can be used to run the project in localhost 
The *docker* profile can be used with the docker-compose YAML file that's included in root project

In theory you only need to use ensure the following:

 1. the projects are using the docker profile
 2. compile the projects using `gradle clean && gradle build`
 3. Run `docker-compose build && docker-compose up`
 
 
 Note: I couldn't test very well this functionality, because I tried to run all the projects and databases on my old machine
 One improvement could to be convert the docker-compose file to a docker swarm file and test on multiples nodes using docker-machine or 
 create Kubernetes compliant files and deploy on Kubernetes, but this exercise would cost ($$$) too much for a simple test project
  



