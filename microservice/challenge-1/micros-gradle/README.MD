
## Usage

It uses Java 11

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
