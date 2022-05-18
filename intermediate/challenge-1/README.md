<h1 align="center">Technical challenge for B-Yond 👋</h1>


## Build

You'll need Docker and docker-compose.

```sh
docker-compose build
```


## Usage

```sh
docker-compose up 
```

## Run tests

```sh
docker compose -f docker-compose.tests.yml up
```


## How to use 

1- Get the token of the first manager stored in the database (the credentials are in the environment variable)

```sh
http://127.0.0.1:8080/login/access-token

Body request : 
	username : "firsr_manager@audela.com"
	password : 0000
```

2 - Store this token in the request header for authentication and authorization of future requests


## API documentation 

```sh
http://127.0.0.1:8080/docs
```


