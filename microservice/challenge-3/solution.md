# Rocket science



Testing is a necessity in our system. We need to test all system functionality on any changes made 
in development phase (Regression test, Smoke test and etc). 
According to [Challenge-3 ](https://github.com/b-yond-infinite-network/amaze-us/tree/master/microservice/challenge-3), 
we need to test our services **separately** and also **together** in order to guarantee stability. 

In proposed solution, testing services separately is done with [Cucumber](https://cucumber.io/) as an 
automated testing framework and although it is possible to still use Cucumber for testing them together, but
[Consumer-Driven Contracts](https://martinfowler.com/articles/consumerDrivenContracts.html) approach is 
selected for such purpose as 
it is widely used in a distributed systems – for both HTTP-based and message-based interactions.

## Getting Started

### Testing services separately
[Test-automation](https://github.com/Hosnidokht/amaze-us/tree/master/microservice/challenge-3/test-automation)
contains 'booster.feature' and 'cargo.feature' scenarios
to test
[Booster](https://github.com/b-yond-infinite-network/amaze-us/tree/master/microservice/challenge-3/booster) 
and [Cargo](https://github.com/b-yond-infinite-network/amaze-us/tree/master/microservice/challenge-3/cargo) 
in '/tanks' and '/api/cargo' routes respectively 
for create, read and delete requests.
~~rest of routes are ignored for now as they are all in the same pattern~~

#### Cucumber coding style tests

Test methods are written in spring Behavior-Driven Development fashion(BDD).
* The **given** part describes the state of the world before you begin 
the behavior you're specifying in this scenario.
* The **when** section is that behavior that you're specifying.
* Finally the **then** section describes the changes you expect due 
to the specified behavior.


#### Running test-automation

docker-compose.automated.yml is created to start testing booster and cargo services.
```
docker-compose -f docker-compose.automated.yml up --build
```

when the tests are finished, running the following is necessary to 
stop and remove generated containers.
```
docker-compose -f docker-compose.automated.yml down
```

NOTE: Using CTRL+c to stop docker-compose results into a failure in the next 
launch.


### Testing services together
Although, I could not find any interaction between given services in this challenge, but to give a solution 
for `testing services together` it is assumed 'cargo-service' (as a consumer) is interacting with 'booster-service' (as a provider) in both HTTP 
and message based.

### Prerequisites
It is necessary to have a big picture of Spring Cloud Contract before going further.

#### Spring Cloud Contract
Spring Cloud Contract is an umbrella project holding solutions that help users in successfully 
implementing the Consumer Driven Contracts approach. In each contract there is one provider and one consumer. 

Services could be connected through HTTP or message brokers.
* In HTTP-based interaction wiremock is used
* In message-based interactions stubTrigger is used

Each provider should define a contract which gives us more trusted feedback than end to end testing. 

Spring Cloud Contract use these contracts to:
* Generates and packages stubs
* Downloads and executes stubs on the consumer side
* Generates and runs contract verification tests on the provider side.
* Spring cloud contract uses wiremock and stubTrigger to create stubs for the consumers
* Consumer uses generated stubs created
  according to the specified contracts and run all interaction tests with the provider

##### Impelmentation

In this approach two services are created as follows:
* [booster-service](https://github.com/Hosnidokht/amaze-us/tree/master/microservice/challenge-3/boosterservice)
* [cargo-service](https://github.com/Hosnidokht/amaze-us/tree/master/microservice/challenge-3/cargoservice)

'booster-service' and 'cargo-service' are provider and consumer respectively.
'booster-service' defines a HTTP-based and message-based contract for 'cargo-service' interactions in groovy files
for POST method in '/tanks' route (available in 'contracts.cargo' package).
Spring cloud contract uses
wiremock and stubTrigger to create stubs for 'cargo-service'. 
Then, 'cargo-service' uses generated stubs created and run all interaction tests with 'booster-service' (available in 'com.challenge.cargo.contracts.booster' package).

By these definitions, it is required to generate stubs on provider side first and run tests in consumer side afterwards.

##### Package naming convention

* 'booster-service' defines a contract to interact with 'cargo-service', So, contracts are in 'contracts.cargo' package.
* 'cargo-service' tests its interaction with 'booster-service' in 'contracts.booster' package

#### Running Consumer driven tests
```
docker-compose -f docker-compose.contracts.yml up --build
```

## Improvements (TO DO)

* Parametrize services to have more dynamic docker-compose file


## Built With

* [Docker](https://www.docker.com/) 
* [Spring](https://spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/) - Test Automation tools
* [Spring Cloud Contract](https://spring.io/projects/spring-cloud-contract)


## Authors

* **Mohammad Hosnidokht** 


## Acknowledgments

* Docker
* Spring framework
* Cucumber
* Spring Cloud Contract
* BDD


