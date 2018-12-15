# Challenge

[Challenge](Exercise.md)... [Accepted](MASTER_PLAN.md)!

*TLDR;*

I chose "App42PaaS-Java-MySQL-Sample" to experiment with *Kubernetes*, Java, MySQL, because I don't have any real experience with them!

With this application, here I demonstrate how to scale a webapp horizontally, thanks to Kubernetes. We leverage features such as Services, Secrets, ConfigMaps and Probes.

I also demonstrate how we can put in place integration tests on the application, using a Docker in Docker Kubernetes cluster.

One thing that has *not* been demonstrated is how to split this application in microservices.
If I had more time, I would have split the application into a UI and API Service (talking to the backend).

# Overview

The sample application has been dockerized and slightly modified for:
* container configurability
* added liveness & readiness healthchecks for Kubeneters

The dockerized application can be tested either with docker-compose or [kubeadm-dind-cluster](https://github.com/kubernetes-sigs/kubeadm-dind-cluster).

Integration tests are written using a very simple bash framework called [Bats](https://github.com/sstephenson/bats).

I tried to be keep requirements minimal.

# Requirements

## Maven

It's a Java project, so I assume Java & Maven to be correctly configured.
See https://maven.apache.org/install.html

## docker

Docker is used in all options, it is a requirement.

Scripts don't use `sudo` to run docker commands, so you need to make sure your use can run docker:
https://docs.docker.com/install/linux/linux-postinstall/

It boils down to add your user to the docker user group.

## docker-compose (default setup)

On some plateforms, docker-compose needs to be installed independly from docker:
https://docs.docker.com/compose/install/

## Kubeadm-dind-cluster

Kubeadm-dind-cluster has been vendored in the repository, but you'll need installed on your machine `kubectl v1.12`, please refere to https://kubernetes.io/docs/tasks/tools/install-kubectl/


### On OSX

There are additional requirements from kubeadm-dind-cluster:

```
brew install jq
brew install md5sha1sum
```

# Build

```bash
./build.sh
```

It will build the application (as a WAR) with Maven, and build a Docker container.
The container is tagged `app42front`.

It is based on a Tomcat, listening on port 8080.
The application is deployed in the ROOT context (so accessed on / base url).

## Image configuration

By environment variable:

* MYSQL_IP_ADDR: address of the MySQL database to connect to (default: db)
* MYSQL_PORT: TCP port of the database (default: 3306)
* MYSQL_DATABASE: Name of the database used by the application (default: )
* MYSQL_USER: Username to connect with (default: user42)
* MYSQL_PASSWORD: User password, if set it has the priority over MYSQL_PASSWORD_FILE (no default)
* MYSQL_PASSWORD_FILE: File containing the password of the user (default:/run/secrets/db_user_password)

# Tests

## Testing Manually

One can start the container manually with Docker:
```bash
$ docker run -it --rm -p 8001:8080 -e MYSQL_PASSWORD='abcd12345' app42front

# Are you there?
$ curl localhost:8001/ping
{"response":"pong"}

# Are you ready to accept traffic?
# (can you communicate with your MariaDB backend?)
$ curl localhost:8001/ready
curl -v localhost:8001/ready
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8001 (#0)
> GET /ready HTTP/1.1
> Host: localhost:8001
> User-Agent: curl/7.54.0
> Accept: */*
>
< HTTP/1.1 503
< Content-Type: application/json;charset=ISO-8859-1
< Content-Length: 17
< Date: Sun, 16 Dec 2018 10:17:04 GMT
< Connection: close
<
* Closing connection 0
{"response":"KO"}

# Not ready, obvisously!
```

Ok, let's give it a database!

## Automated Testing

You can test either with docker-compose or kubeadm-dind-cluster.

The former is faster, and easier to work with for interative development,
while the later offers much greater powers in terms of testing and is much more prod-like.

So both are useful :)

Tests are run from the provided script `./integration-tests.sh`, which can be configured with environment variable:
* USE_K8S_DIND: set it to enable dind-cluster based testing, otherwise compose is used
* DEBUG: set it to not automatically destroying the test environment.

### With docker-compose

```bash
$ ./integration-tests.sh
Creating network "challenge-1_default" with the default driver
Building web
# ...
Waiting a bit for the cluster & apps to be up...

Running tests

 ✓ Application installed in ROOT
 ✓ Fetch logo
 ✓ /home endpoint (first time is slow)
 ✓ /ping endpoint
 ✓ /ready endpoint

5 tests, 0 failures
Stopping challenge-1_web_1 ... done
Stopping challenge-1_db_1  ... done
Removing challenge-1_web_1 ... done
Removing challenge-1_db_1  ... done
Removing network challenge-1_default
```

### With kubeadm-dind-cluster

Or you can test with kubeadm-dind-cluster:
```bash
$ USE_K8S_DIND=1 ./integration-tests.sh
# ...
secret/mysql-root-password created
secret/mysql-user-password created
configmap/app42-common-env created
service/mysql created
deployment.apps/mysql created
service/myapp created
deployment.apps/myapp created

Waiting a bit for the cluster & apps to be up...

Running tests

 ✓ 3 Pods are running
 ✓ 2 Services are running
 ✓ All fronts are up and running
 ✓ Frontend service is up and running

4 tests, 0 failures
* Removing container: 1b1d0b6597d5
1b1d0b6597d5
* Removing container: d92ef865bd60
d92ef865bd60
* Removing container: b9d90a3941b4
# ... rest of the cleanup happen
```

### DEBUG=1 Usage

Setting DEBUG environment variable allows you to inspect and reuse your test environment.
Once your are done, you can destroy the testing environment:
```bash
# With docker-compose:
$ ./integration-tests.sh destroy

# With DinD Cluster:
$ USE_K8S_DIND=1 ./integration-tests.sh destroy
```
