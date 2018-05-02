# B Yond Java/MySql Microservices

## Overview

* the webapp was updated and rebuilt so that it's compatible with Java 8
* the webapp development work was done under IntelliJ
* the IntelliJ project project files are included


## Scalability/Architecture

* the webapp has been built to run under Tomcat under Docker
* one or more tomcat containers (with each container hosting one webapp instance) can be started without the need to re-orchestrate the system
* scaling up/down of the number of webapps is done with the docker-compose scale command
* there is a single MySql instance running in its own Docker container
* scalability of the database in this implementation is limited
* the web app can be configured to use any instance of MySql thereby allowing some scaling of the database should MySql be running on a cloud service such as AWS
* configuration of the MySql connection parameters is done with environment variables located in an XML file, context.xml
* changing content.xml does not require rebuilding of the webapp, it requires only re-orchestration of the system
* orchestration of the entire system is accomplished via Docker Compose and an associated file, docker-compose.yml
* an nginx load balancer is running in front of the tomcat servers and performs basic round-robin load balancing
* Docker's built-in DNS resolver is used in conjunction with nginx and allows nginx to reliably start up even if the upstream Tomcat servers are not ready


## Minor webapp improvements/changes

The web app has been modified in the following ways:

* configurationn data for the MySql connection is now read from environment variables supplied by Tomcat rather than from the properties file included with the original project
* simple SQL injection protection has been added by the use of inserts into the database using prepared statements
* the back button on the database listing and error web pages now works
* the clear button now works to correctly delete user data from the database


## Building the system

The first step in building the system is to build the webapp .WAR file.  This is done with Maven:

* go to the folder that contains the pom.xml file (folder tomcat/webapps/JavaMySql)
* execute the following in a terminal window:

* `mvn clean`
* `mvn package`

This will generate the file `JavaMySql-1.0.war` in the folder tomcat/webapps/JavaMySql/target/

Once the WAR file is built then the system can be built with docker-compose:

* in a terminal window go to the root directory of the project (the directory that contains the file docker-compose.yml)

* `docker-compose build --no-cache`


## Running the system

* after a successful build (and assuming Docker is running) the system can be started from a terminal window with:

* `docker-compose up`

* once the system is running the webapp can accessed from a web browser with:

* `localhost:5000/JavaMySql-1.0`


## Scaling the system

* the number of webapps running under Docker can be increased or decreased with the following command:

* ` docker-compose scale tomcat=n` where n has been tested on Docker running an i7 Mac Mini to a maximum of 20


## Bringing the system down

* in the terminal window where `docker-compose up` was started press `Ctrl-C` and then run the command `docker-compose down`


## Changing the database connection parameters

* the database connection parameters can be changed by edited the file app_context.xml in the directory tomcat/conf

* the system should then be brought down and rebuilt and restarted with the commands listed above

* to use the containerized instance of MySql the `java.mysql.ip` environment variable must be set to `mysql`

* when the containerized instance of MySql is used the data is persisted in a Docker volume relative to the Docker project so that the data is not destoyed then the `docker-compose down` command is issued.


## Debugging the webapp

The webapp was debugged using IntellJ.  The DockerFiles and docker-compose.yml file expose the debugger port 8000.  To debug the app I was only able to attach the debugger with a change to the docker-compose.yml file.  The required change (in the tomcat section) is:

* remove:
    expose: - "8080"

* replace with:
ports: - "8080:8080" - "8000:8000"

After doing so the IntelliJ debugger will attach to the running webapp.  The side effect of doing this is that the number of Tomcat servers cannot be scaled to more than one.



## Wish list

Should the time have been available I would like to have:

* implemented a scalable solution for MySql although this may have been difficult without Docker Swarms and other software
* added more robust error and exception handling in the webapp (currently no error messages are shown to the user) but it should be realized that the purpose of this exercise was not to develop a robust webapp
