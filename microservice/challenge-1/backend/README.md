<!-- ABOUT THE PROJECT -->
## About The Project

The goals of this sumbission are:
* Showcase the Java Spring 2 CRUD fully scalable microservice
* Attach a basic UI written in Angular 8 with my modifications to fit my backend model (https://www.devglan.com/spring-boot/spring-boot-angular-example);
* Deploy K8S(GKE) in the GCP in order to deploy my components
* Deploy PostgreSQL DB in GCP as a VM
* Provide a fully workable CI/CD pipeline for backend microservice
* Show a working flow in the cloud

I will try to keep this README small and simple with only important insights.

### Built With
Techs/Tools used in the showcase.
* [Java Spring 2](https://getbootstrap.com)
* [Angular 8](https://jquery.com)
* [GitLab CI/CD](https://laravel.com)
* [GCP, GKE, GCR](https://laravel.com)
* [PostgreSQL](https://laravel.com)

### Quick Walkthrough
The UI is available in here: http://35.225.55.107:30002/

A very simple UI to show basic functionality of the redesigned application. Runs in GCP(GKE)
The API is accessible through
* http://35.238.25.85/api/v1/

The pipeline is accessible through here:
* [CI/CD Pipeline](https://gitlab.com/stormbringerdp/amaze-us/pipelines)

Basic CRUD(GET, POST, PUT and DELETE) functionality is supported on the users/ path:

* GET call http://35.238.25.85/api/v1/users/{id} - id is optional to get user by id
Response:
```sh
{
    "status": 200,
    "message": "Successfully fetcheed user with id 1",
    "result": {
        "id": 1,
        "name": "Igor",
        "email": "igor@gmail.com",
        "description": "first user in"
    }
}
```

* POST call http://35.238.25.85/api/v1/users/ - create a user
Request body:
```sh
{
    "name": "Igor21",
    "email": "igo22s1r@gmal.com",
    "description": "first user in"
}
```
* PUT call http://35.238.25.85/api/v1/users/{id} - updates a user by id
Request contains the same body as for creation of a user

* DELETE call - http://35.238.25.85/api/v1/users/{id} - delete user by id
No body is required

Component runs in GCP(GKE)
![GCP](https://github.com/stormbringerdp1/WIGTSFinal/blob/master/beyond.png)

<!-- ARCHITECTURAL DECISIONS -->
## Architectural decisions 

I have choosen to redesign the following project https://github.com/shephertz/App42PaaS-Java-MySQL-Sample

Here is the list of decisions I took before doing refactoring/redesigning:

1. Use Java Spring 2.1.x with Gradle 5.x.
* I am familiar with Java, so choosing this language speeds up the development process.
* I find the groovy/kotlin style of Gradle nicer than XML style of Maven. So, for trhis project I chosen Gradle.
2. Used MVC concept to separate the original monolith into modularized, loosely coupled component.
* Web - contains exposed apis and classes pertinent to the web layer of the component
* Entity - DB entities
* Service - main layer where all the logic happens if any
* Repository - DB logic happens here
* Configuration - configs for different layers/middleware, such as CORSFilter
* db - flyway migrations

3. Use of PostgreSQL as the primary database 
* Both PostgresQL and MySQL are good choses. I have chosen Postgres because of the [CrunchyData](https://www.crunchydata.com/) framework we can use if we need to scale in the cloud. 

<!-- BACKEND INSIGHTS -->
## Backend Insights 

* applicastion.properties

  I have used properties files to make distinction between deploying locally and in the k8s
  application.properties - used locally

* application-clustered.properties
  
  used in k8s clustered. This profile is passed to the java on runtime through k8s manifest.
  The URL and DB credentials are supplied to the app inside the k8s environment. It is achieved by the use of the K8S ConfigMaps. The URL name resolution is happening inside the cluster by the k8s DNS internal services resolution.

* Dockerfile

Provided the Dockerfile for the application. Used by CI/CD as well

* K8S ConfigMaps

I have build the component with the idea that the configuration has to be provided by the environment and not by the developer in the code. Basically, the component will behave in the expected way based on the environment it runs on. So, the developer should not be concerned about how to deploy the component. Component will connect to proper components and datasources based on the env.
I have used K8S ConfigMap to provide configuration to the Java in runtime.

* beyond-backend.yaml

Contains k8s manifest for the backend component

* postgres-external.yaml

Manifest for the communication with the postgres. This points to an external DB. As you can see, if the DB url and/or port changes, we have to change it in the config map only instead of changing it in the config of every component that uses the db. This design is very portable and easy for deployment. Developers do not need to care when deploy to prod. All will be automated.

* prod-cm.yaml

This is the config file which should be deployed in the k8s cluster and it contains all the configurstions for the microservices. In my case it contains the url, username and password for the db. In normasl flow this config file is not part of repository and is handeled separately. In this way nobody sees sensitive information. Only people who have access to the cluster can see it. By using configmap, we automate the deployment process and control the behavior of the component through the environmewnt it runs on, instead of from the code. This is much less erroprone.

  ### Backend future improvements

* Test code coverage and quality

I have provided some very basic unit testing and one integration test to show that I am familiar with them)) For the next iteration, I would increase the number of tests as well as their quality.

* Fields validations

I do not provide custom exception on every generic exception that the code can throw. For example if somebody provides a name field of more than 50 chars, there will be an exception thrown. I do not explicitely handle it, so it will be thrown in the controller. The exception is clear, but if handeled explicetely, will not polute the output and will display a nicer message.

* Exception handling

I have provided one exception hadling with validation on the email field. As stated earlier, exception on field vslidation ccan be one more to include.

* Authontication/Authorization

The token generation should be a part of a separate component.
This one could act based on token decode from the header and act on contextual permission.
This is to implement in the future iteration.

<!-- FRONTEND OVERVIEW -->
## Frontend Overview

There is not much I can say about frontend. It is an Angular 8 implementation that I took from https://www.devglan.com/spring-boot/spring-boot-angular-example

I have removed Login part and modified model to fit my backend implementation. Showcased that I can work in the UI as well up to a certain point)
The UI does not handle exceptions and custom erros thrown from the backend. I have attached it, so you can see the full flow of application from the UI to backend and back. All deployed in the GCP cloud.

<!-- CI/CD PIPELINE OVERVIEW -->
## CI/CD Pipiline Overview
I have implemented the full pipeline for the backend component in GitLab CI/CD. It gets triggered on every commit.
The pipeline is available here(check it out!!): [CI/CD Pipeline](https://gitlab.com/stormbringerdp/amaze-us/pipelines)

Pipeline steps are:
* build - build java jar
* unit tests - runs unit tests only
* integration tests - runs integration tests on real db
* docker - dockerize and push to GRC
The following lines are important to build distinc images. The docker tag is based on git commit message. This makes sure that we keep track of images and that deploy to k8s step properly rollout the updates.
```sh
export TAG=$(git log | head -n 1 | awk '{print $2}')
docker push ${DOCKER_IMAGE}:${TAG}
```
* deploy to K8S - deploy the docker into GKE cluster
In order to make sure that we automate rollout based on the commit I use the following approach:
```sh
export TAG=$(git log | head -n 1 | awk '{print $2}')
export LATEST=$(cat $CI_PROJECT_DIR/microservice/challenge-1/backend/k8s/${COMPONENT_MANIFEST} | grep  'byond-project/beyond-backend:' | sed 's/[^,:]*://g')
sed -i -e "s/${LATEST}/${TAG}/g" $CI_PROJECT_DIR/microservice/challenge-1/backend/k8s/${COMPONENT_MANIFEST}
kubectl apply -f $CI_PROJECT_DIR/microservice/challenge-1/backend/k8s/${COMPONENT_MANIFEST}
```
it gets git commit, replaces the image tag inside k8s manifest with the proper tag and then apply the change in k8s. This ensures that on every commit we have a proper rollout based on git tag.

The pipeline is only implemented for the backend component. Frontend docker is built and pushed to k8s by me manually.

<!-- GCP, GKE, GCR OVERVIEW -->
## GCP, GKE, GCR Overview

In order to show the scalability of my component, I had to deploy k8s cluster in GCP. It is basic 2 nodes cluster. I use GCR to host my docker images.
In order to load balance the traffic, in my service definition for backend I used type LoadBalancer. GKE, then balances the traffic among multiple replicas. I included k8s manifests in the respective projects.
I have not implemnented load balancing for frontend. As an improvement I can use anothe LoadBalancer from GKE or use Istio/Ambassador or any other ingress controller for traffic balancing.

The end!!!