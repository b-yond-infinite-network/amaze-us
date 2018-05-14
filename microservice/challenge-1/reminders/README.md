#B.Yond challenge : jaxrs-reminder-app

This is a simple project showcasing how to break monolithic app into micro services. It consists of 3 back-end APIs.

###reminder-core-api
This API receives requests to create/read reminders :
* /api/reminders/v1/reminder  (GET, POST) : Example of payload : {"text":"Wake up","recipient":"siriman0701@gmail.com","cron":"0/40 * * * * ?"}
* /api/reminders/v1/reminder/{reminderId} (POST) : Example of payload : {"text":"Wake up","recipient":"siriman0701@gmail.com","cron":"0/40 * * * * ?"}

Techologies: Spring Core, Spring Boot, Spring Data (JPA annotations), Spring Actuator, Orika (data mapping), lombok, H2 Database (could run on MySQL easily), Spring Test, AssertJ, Mockito.

* Note that calling the first endpoint will persist a reminder then send an email based on the provided CRON expression.

###scheduler-api
This API exposes an endpoint to schedule a reminder using Quartz Scheduler : 
* /api/scheduler/v1/reminder (POST) : Example of request : {"id": "eefc2a9d-ff22-4848-b780-aaf505e00245","cron": "0/5 * * * * ?"}

Techologies: Spring Core, Spring Boot, Spring Actuator, Quartz, lombok, H2 Database (could run on MySQL easily), Spring Test, AssertJ, Mockito.


###email-sender-api
This API exposes an endpoint to send e-mail via Amazon Simple Email Service : 
* /api/mails/v1/mail (POST) : Example of request : {"sender": "siriman0701@gmail.com","recipient": "siriman0701@gmail.com","subject": "Reminder alert","text": "Wake up"}

Techologies: Spring Core, Spring Boot, Spring Actuator, Spring Cloud AWS, Javax Mail, lombok, H2 Database (could run on MySQL easily), Spring Test, AssertJ, Mockito.

##Setup projects locally

###In Development 
1. This is a Maven project on the back-end API side, simply import the maven projects into IDEA or Eclipse
2. Each API has an Application.java with a main, simply execute it.

reminder-core-api is accessible through http://localhost:8080

scheduler-api is accessible through http://localhost:8081

email-sender-api is accessible through http://localhost:8082

###Vagrant (Runtime)
1. run build.sh or execute the commands in the file
2. vagrant up
3. Wait (take a break)
4. browse to http://localhost:8080

###NGINX + Docker
Within the Vagrant box loaded, NGINX and Docker are installed to run the complete service. NGINX acts as a proxy which wraps the calls made to the other instances and expose them through a single URL on port 8080. Each project as a Dockerfile defined in order to create a image that can be deployed on the docker engine running within the vagrant box. See vagrant_setup.sh for details.

Dockerfile are available in each project folder and describes how it deploys using alpine unix, lightweight distro.

/etc/nginx/nginx.conf is the nginx configuration which uses proxy_pass to serve everything from a central server and query the docker runtimes exposed on different local ports (like locally).

##Versions

Everything should work on the latest versions but just in case here are the versions I used to develop this project

IDEA 2018.1

$ java -version

1.8.0_161-b12

$ mvn -version

Apache Maven 3.5.3

$ vagrant -v

Vagrant 2.1.1