# B-Yond challenge

## Requirements:

* Apache Maven
* Docker
* Kubectl and minikube
* Java 8

## Usage

1. Clone this repo
2. git checkout musab_mirza
3. eval $(minikube docker-env)
4. cd <project_dir>/amaze-us/challenge-1/frontend && mvn clean package -DskipTests (create docker image challenge/frontend:latest)
5. cd <project_dir>/amaze-us/challenge-1/userservice && mvn clean package -DskipTests (create docker image challenge/frontend:latest)
6. kubectl apply -f <project_dir>/amaze-us/challenge-1/kubernetes/mysql-deployment.yml
7. kubectl apply -f <project_dir>/amaze-us/challenge-1/kubernetes/userservice-deployment.yml
8. kubectl apply -f <project_dir>/amaze-us/challenge-1/kubernetes/frontend-deployment.yml

This creates:

* 1 frontend service pointing to 3 frontend pods from image challenge/frontend:latest
* 1 userservice service pointing to 2 userservice pods from image challenge/userservice:latest
* 1 mysql service from image mysql:latest with one persistant volume claim

## Access the webapp

You can access the webapp at: http://localhost:8080


## Author

Musab Masood Mirza (musab@musabmasood.com)

Please contact me if you have any issues running this application.