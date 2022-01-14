# ReminderAppl 

## Context:

Purpose of the project is to refactor a monolithic JAXRS based simple Reminder Application in Java into a microservice application deployable locally and on cloud.
The existing application got a set of services that store reminders and support CRUD operations through MySQL based datastore. There is no sophisticated business logic.
Apart from CRUD there is only one search criteria which finds reminders between date ranges. Existing application doesn't have a frontend module.


## Testing:
There are 3 kind of tests. Only certain cases being tested. Test Cases are not exhaustive

**Unit Tests** – Test Service layer logic for individual units of logic
**Integration Tests** – Verify the logic utilizing in-memory data store (H2)
**Contract Test** – Verify contract from Server side as expected by Client through Spring Cloud Contract framework.


## API Documentation:
Swagger annotations (v3) has been used for API documentation purpose. Post deployment, the API documentation will be available at the following URI as per environment.

	**Swagger URL**
	Cluster environment: http://<service-name>:<service-port>/swagger-ui/index.html
	Local: http://localhost:8080/swagger-ui/index.html

  
## CI/CD Pipeline:

Considered gitlab as the CI/CD tool for deployment into Kubernetes cluster. It is not fully tested as it require setup of environment for cluster with gitlab. 
Helm charts will be utilized for K8 deployment. Values file,environment variables are used for configuration. However ideally a separate configuration server is best practice.
  ```
    Refer to gitlab_ci.yml for CI/CD pipeline
	
  ```

## Local Testing:

  **Download mysql docker image**
   ```
   docker pull mysql
   
   ```
   **Execute docker compose file to bring up application**
   This process will build the app,create docker image and then create the containers and their interlinking.
   ```
   Move to Docker-Compose directory
   docker compose up -d
   docker images
   docker ps
   
   ```
   **Test the application through Postman**
   Use the collection *Reminder-App.postman_collection.json* as a starter for testing the application using Postman Tool.
   
   

## Future Improvement:
  
Cross cutting concerns in Microservice should be centrally managed like logging,tracing,configuration. 
Security aspect is another concern. For backend service like reminderapp security provided by cluster environment should be enough. 
Tools like Vault should be used for secret management of user credentials.

