FROM openjdk:12-alpine

MAINTAINER Igor Tcherniavski <igor.tcherniavski@ericsson.com>

#VOLUME /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/

#RUN ls -la /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/backend-latest.jar
#ADD /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/backend-latest.jar /backend/backend-latest.jar
ADD /mnt/backend-latest.jar /backend/backend-latest.jar
ENTRYPOINT ["java", "-jar", "/backend/backend-latest.jar"]
# Document that the service listens on port 8088.
EXPOSE 8088