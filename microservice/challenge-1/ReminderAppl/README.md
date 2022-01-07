Context:

Purpose of the project is to refactor a monolithic JAXRS based simple Reminder App in Java into a microservice app deployable locally and on cloud.
The existing application got a set of service that store reminders and support CRUD operations through MySQL based datastore. There is no sophisticated business logic,
apart from CRUD there is only one search criteria which finds reminders between date range.


Testing:
There are 3 kind of tests. Only certain cases being tested. Test Cases are not exhaustive

1)	Unit Tests – Test Service layer logic for individual units of logic
2)	Integration Tests – Verify the logic utilizing in-memory data store (H2)
3)	Contract Test – Verify contract from Server side as expected by Client through Spring Cloud Contract framework.


API Documentation:
Swagger annotations (v3) has been used for API documentation purpose. Post deployment, the API documentation will be available at the following URI as per environment.

Swagger URL:
Cluster env: http://<service-name>:<service-port>/swagger-ui/index.html
Local : http://localhost:8080/swagger-ui/index.html

  
CI/CD Pipeline:

Considered gitlab as the CI/CD tool for deployment into Kubernetes cluster. It is not fully tested as it require setup of environment of cluster with gitlab. 
Helm based charts will be utilized for K8 deployment. Values file,environment variables are used for configuration. However ideally a seperate configure server is best practice.

Further Improvement:
  
Cross cutting concerns in Microservice should be centrally managed like logging,tracing,configuration. Security aspect is another concern. For backend service like reminderapp security provided by cluster environment should be enough. Tools like Vault should be used for secret management of user credentials.

