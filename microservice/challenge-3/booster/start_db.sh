#!/bin/bash

#Start a docker container for mysql 
docker run --rm --name mysql -p55455:3306 -e MYSQL_ROOT_PASSWORD="Guest0000!" -e MYSQL_DATABASE=todoapp -e MYSQL_USER=guest -e MYSQL_PASSWORD="Guest0000!" -d mysql:5.5
