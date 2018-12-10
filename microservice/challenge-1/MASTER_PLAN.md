# Rational

I am chose this exercise in order learn about following technologies:
* Java Dev and Ecosystem (e.g. maven)
* MySQL
* Kubernetes
* Ansible if time permits

# Approach

I'll take an Agile approach to be able to deliver something that works
at any point of time, I am time boxing myself for this exercise.

The challenge includes a refactoring sub-challenge, 
in order to make I don't break this business critical app, first we'll need:
* automated build and unit testing (let's use Docker to limit system dependencies)
* automated integration tests (we'll use Docker Compose)

Once we'll have this safety net in place we can move on to make it scale!

We'll start by scaling the front (much easier :)):
* fix code base if neccessary (e.g. not resilient to db disconnect)
* add healthchecks endpoints
* add support for K8s style secrets
* integration tests with kubeadm-dind (over minikube, again to limit dependencies)
* ...

Then, in theory should go more prod-like (e.g. deploy to GKE, etc),

I'll probably skip that and try to scale mysql (e.g. look @ vitess.io)

Let's go! 




 
