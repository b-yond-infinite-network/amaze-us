# Challenge 1 - Saving the cathedrals

The Monolithic service (Building) has been sliced into multiple microservices providing similar functionalities
 + postapp 
 + getapp
 + discoveryservice
 + angular UI

getapp microservice is Spring Boot application used to fetch the data from MySQL database to show on the home page. postapp is another Spring Boot application used to store a new form in MySQL database. Both the microservices will be Eureka Client service and register themselves with the discoveryservice. The Dockerfile in the applications will be used to create a docker image and push to Docker Trusted Registry (DTR). 

The monolithic service has been sliced into different components since traffic to each of the components i.e. no. of http request will vary. Based on the http request, the orchestrator will scale up/down the no. of containers for respective microservice.

The angular UI renders the web page where the contact us form is available as well as data can be viewed.

Since with microservice architecture maintainability and scalability is of prime importance, we have build service registry and discovery mechanism which will maintain indivual microservices addresses. The discoveryservice will provide the service registry and discovery server, where other microservices can register and discover other services. With Eureka Server, the Spring Boot Admin will provide service monitoring capability.

##Auto-Scaling
The Jenkins pipeline will poll the endpoints periodically and monitor the number of incoming HTTP requests to microservices since each microservice has spring actuator that provides the metrics through /actuator/metrics. Based on the http traffic, application's container will be scaled up/down. For scaling up the application, the Jenkins pipeline will fetch the image from DTR and spin a container. For graceful shotdown i.e. scaling down, it will use the endpoint /actuator/shutdown of the microservice. Jenkins pipeline will pull the list of microservices from Eureka Server since all the microservices register with discoveryservice. The discoveryservice endppint /eureka/appa/<microservice name> will be used for fetch all the instances of that microservice.

For MySQL used remotemysql.com database instance.
** Another orchestrator, cAdvisor,  can be used to get container metrics and scale up/down using Docker Swarm

## Stack
Java 1.8
Spring Boot 2.1.7
Eureka Service Discovery
Angular CLI
MySQL
Docker
Jenkins
