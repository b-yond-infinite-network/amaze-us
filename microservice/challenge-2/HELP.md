# B-Yond, The second challenge, Save the Humans

Bots started an invasion to our planet. Regarding the latest research they leave some patterns in their reviews on amazon. Here we provde a REST API on the top of a Machine Learning microservice. It supposed to finds this patterns...
We've downloaded and mixed some datasets from here: http://jmcauley.ucsd.edu/data/amazon/
That data is being used to prepare the dictionary.txt file. dictionary.txt is the experience we make for the machine about human reviews.

## Architecture

We've used Microservices architecture. The services is stateless. So you can easily bring more instances up to scale out.
This service supposed to provide a low-level API. So it needs to be deployed behind firewalls, HA and non-blocking high-level APIs.

## Getting Started

1. Compile and buid the jar file:
```
mvn clean install -DskipTests
```

You can run service by one of the following ways:
```
mvn spring-boot::run
```

OR

Starting the project by building a docker image and running it withing the container:
```
docker-compose up
```

Then access the API online document using the following address:
```
http://localhost:8080/swagger-ui.html
```

Note:
After running service it starts to train itself. It takes a fe

### Prerequisites

The learner needs at least 2GB ram. It uses a dictionary that downloaded from here:

## Running the tests

To run the tests just run the following command:
```
mvn test
```

Note: 
The learner needs to be trained. It takes a couple of minutes. Ones it trained, the tests will be running one after one.

## Try It

Request:
```
curl -X POST "http://localhost:8080/smell" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"review\": \"Hi, map saving function is just coming out. We received a lot of advice from customers and will consider them seriously. Our developing department will complete more practical features gradually. We appreciate all good ideas from every customer and hope we could work with customer together to make our product better\", \"suspectedWords\": [ \"amazing\", \"human\" ]}"
```

Response:
```
{
  "review": "hi  map saving function is just coming out  .   we received a lot of advice from customers and will consider them seriously  .   our developing department will complete more practical features gradually  .   we appreciate all good ideas from every customer and hope we could work with customer together to make our product better",
  "verbose": "Seems the reviewer is a human being.",
  "suspectedWords": [
    "amazing",
    "human"
  ],
  "matchingSimilarWords": [],
  "matchingWords": [],
  "isBot": false
}
```

So he/she is a human! because isBot is false. There is also a verbose field.

Note:
You can try it with other suspected words. You are not limited to "amazing" and "human" keywords. The learner also will try the similar words as well.


See another sample:
```
curl -X POST "http://localhost:8080/smell" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"review\": \"Hi, map saving function is just coming out. It is exciting and we received a lot of advice from customers and will consider them seriously. Our developing department will complete more practical features gradually. We appreciate all good ideas from every awesome customer and hope we could work with customer together to make our product better\", \"suspectedWords\": [ \"amazing\", \"human\" ]}"
```

Response is:
```
{
  "review": "hi  map saving function is just coming out  .   it is exciting and we received a lot of advice from customers and will consider them seriously  .   our developing department will complete more practical features gradually  .   we appreciate all good ideas from every awesome customer and hope we could work with customer together to make our product better",
  "verbose": "There is a small likelihood. But it is OK. But still I think he/she is a human being.",
  "suspectedWords": [
    "amazing",
    "human"
  ],
  "matchingSimilarWords": [
    "awesome"
  ],
  "matchingWords": [],
  "isBot": false
}
```

While the service knows she/he is not a machine, but "awesome" also being marked by service! Read the verbose:
"verbose": "There is a small likelihood. But it is OK. But still I think he/she is a human being.",


## Deployment

The project is dockerized now. Just use docker for deployment.


## Health

Service information:
```
curl http://localhost:8080/actuator/info
```

Service health:
```
curl http://localhost:8080/actuator/health
```

The project is dockerized now. Just use docker for deployment.


## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot, the glue framework, provides DI, REST Controllers.
* [DL4J](http://deeplearning4j.org) - The deep learning framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Swagger](https://swagger.io/) - Used to document the API

## Authors

* **Amir Sedighi**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
