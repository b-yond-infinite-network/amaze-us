
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



