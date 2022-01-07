FROM openjdk:8-jdk-alpine

#USER root


RUN apk update && apk upgrade && apk add --no-cache bash
RUN apk --no-cache add curl


ARG JAR_FILE=target/reminder-app-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} reminderapp.jar

 
ENTRYPOINT ["java","-jar","/reminderapp.jar","--spring.profiles.active=dev"]
