# Rocket science

For such a problem we could use Spring cloud contract and test automation tools like Cucumber to automate test cases with the help
of Jenkins. So, Once a developer pushes the code, automatically all regression, smoke or any other test runs.

Spring Cloud Contract provides a contract definition language.
* Generates and packages stubs from contracts
* Downloads and executes stubs on the consumer side
* Generates and runs contract verification tests on the provider side.
And for the consumer side Consumer Driven contract testing is used.

Microservices could be connected through HTTP or message brokers.
* In Http we use wiremock
* In Message driven contract we use stubTrigger

These contracts gives us more trusted feedback than end to end testing. Although for end to end testing we use Cucumber features.

## Getting Started

This solution is given for two of three microservices containing:
* booster-service
* cargo-service

And as the same scenario could be used for all URLs, the booster /tank URL has been tested in our solution.

For automated part a project called test-automation is given.

### Prerequisites

Each provider should define a contract. In our case booster-service defines a contract for POST method in /tank URL. Spring cloud contract uses
wiremock and stubTrigger to create stubs for the consumers (In our case cargo-service). Then, Each Consumer is able to use generated stubs created
according to the specified contracts and run all interaction tests with the provider.  

So, it is required to generate stubs on provider side first:

```
cd boosterservice
mvn clean test

```

### Running Tests in consumer

```
cd cargoservice
mvn clean test
```


It is assumed all the services are running locally.
## Running test-automation

It is assumed a test-automation is runnable when the microservices are running and required information for test scenarios in feature files 
are given accordingly.  

### Cucumber coding style tests

Test methods are written inspring Behavior-Driven Development (BDD).
* The given part describes the state of the world before you begin the behavior you're specifying in this scenario.
* The when section is that behavior that you're specifying.
* Finally the then section describes the changes you expect due to the specified behavior.


## Deployment (TO DO)

* Dockerize application
* Using Cloud Config for given properties


## Built With

* [Spring](https://spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/) - Test Automation tools
* [Spring Cloud Contract](https://spring.io/projects/spring-cloud-contract)


## Authors

* **Mohammad Hosnidokht** 


## Acknowledgments

* Spring framework
* Cucumber
* Spring Cloud Contract
* BDD


