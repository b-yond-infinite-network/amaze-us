# Challenge 1 - Saving the cathedrals

Once upon a time, there was a kingdom of monolithic apps. 

All the buildings where giant and tightly architected to reach as high as possible. 
Each tower touched the sky, one patch at a time. Their bridges and hallways all hanging by the threads and little hacks 
accumulated over the years.

But a threat is looming...

The biggest of the biggest earthquakes is about to strike underneath the kingdom. 

From what the crazy mad scientists tell you, only the apps that have no patch and no hacks will survive.  
Knowing the state of all those apps, only the house and shop will be left if you don't do something quick.

But you're a Knight of ContainerD - you know you can save them all.   
You just have to slice those monoliths up in the right way.


These are the building you can save:
- https://github.com/shephertz/App42PaaS-Java-MySQL-Sample
- https://github.com/xxfast/jaxrs-reminder-app
- https://github.com/spring-projects/spring-data-keyvalue-examples
- https://github.com/salesagility/SuiteCRM

Choose one; choose the orchestrator you want to use; and make it scale!

## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-1)
+ Push your change inside a Pull Request to our master

## Stack

* React (Frontend)
* Spring Boot, Spring Data Redis (backend)
* Redis (Database)
* Docker 19.03.5
* Kubernetes 1.17
* minikube v1.6.2

## Plan

* Challenge: **Challenge 1**
* App to work: https://github.com/spring-projects/spring-data-keyvalue-examples
* Build app using gradle: **DONE**
* Run app using tomcat dockerized: **DONE**
* Split into backend and frontend: **DONE**
* Study backend: **DONE**
* Study frontend: **DONE**
* port code to Spring Boot: **DONE**
* port code from JSP to react: **DONE**
* Multi stage Dockerfile (maven + openjdk): **DONE**
* Multi stage Dockerfile (node + nginx): **DONE**
* Setup Ubuntu machine: **DONE**
* Install minikube: **DONE**
* Helm: **DONE**
* Scale application using k8s: **DONE**
* Improve microservice (frontend): *Incomplete*
  * pages: login, home (list and create posts), timeline (list posts), mentions
* Improve microservice (backend): *Incomplete*
* Draw Architecture Diagram: attached to email
* Jenkins build: TODO
* Jenkins unit test: TODO
* Redis Cluster: TODO
* Swagger: TODO


## Docker Compose (all)

```bash
# build/run frontend, backend and redis
docker-compose up --build --remove-orphans
```

* Frontend: http://localhost:3000
* Backend: http://localhost:8080

## Maven: Test, Package and Run

```bash
cd backend
# only redis on localhost
docker-compose up redis
# tests for backend
mvn test
# package
mvn package
# run backend
java -jar target/challenge1-backend-0.0.1-SNAPSHOT.jar

# frontend
cd frontend
# build
npm install
# run
npm start

# create a user called thiago
# as I did not develop Spring Security, all requests need user thiago

# clean up
docker-compose down
docker-compose rm
```

## Helm Deployment

```bash
helm repo add stable https://kubernetes-charts.storage.googleapis.com
helm repo update
helm search repo redis

kubectl create namespace challenge

# redis (exposed by NodePort)
helm upgrade \
  --install \
  --namespace challenge \
  --set cluster.enabled=false \
  --set usePassword=false \
  --set master.service.type=NodePort,master.service.nodePort=30001 \
  redis stable/redis

export NODE_IP=$(kubectl get nodes --namespace challenge -o jsonpath="{.items[0].status.addresses[0].address}")
export NODE_PORT=$(kubectl get --namespace challenge -o jsonpath="{.spec.ports[0].nodePort}" services redis-master)

# connect to redis (optional)
sudo apt install redis-tools
redis-cli -h $NODE_IP -p $NODE_PORT

# backend (node port 30200)
helm upgrade --install --namespace challenge backend helm/backend/

# frontend (node port 30100)
# set REACT_APP_API_URL with backend IP and PORT
helm upgrade --install --force --recreate-pods --namespace challenge \
  frontend helm/frontend/ \
  --set configValues="window.REACT_APP_API_URL='http://192.168.99.100:30200'"

# create a user called thiago
# as I did not develop Spring Security, all requests need user thiago

# check Helm releases
helm --namespace challenge ls
kubectl -n challenge get pods
kubectl -n challenge get services

# wait untill all services are up

# delete helm chart deployment (optional)
helm -n challenge delete frontend backend redis
```

## Kubernetes

## Scale up/down

```bash
# Check number of pods
kubectl -n challenge get pods

# Scale up backend deployment to rs=3
helm upgrade --install --namespace challenge \
  --set replicaCount=3 \
  backend helm/backend/

# Scale up frontend deployment to rs=3
helm upgrade --install --force --recreate-pods --namespace challenge \
  --set replicaCount=3 \
  --set configValues="window.REACT_APP_API_URL='http://192.168.99.100:30200'" \
  frontend helm/frontend/
```
