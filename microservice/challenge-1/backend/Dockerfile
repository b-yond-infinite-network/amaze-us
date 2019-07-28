FROM openjdk:12-alpine

MAINTAINER Igor Tcherniavski <igor.tcherniavski@ericsson.com>

#VOLUME /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/
WORKDIR /backend
ADD . /backend
RUN ls -la .
RUN ls -la /backend
#RUN ls -la /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/backend-latest.jar
#ADD /builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs/backend-latest.jar /backend/backend-latest.jar
#RUN ls -la /mnt
#RUN --mount type=bind,source="$(pwd)"/target,target=/app
#RUN --mount=src=/builds/stormbringerdp/amaze-us/microservice/challenge-1/backend/build/libs,dest=/backend,ro=true,type=bind
#RUN ls -la /
#RUN ls -la /backend
#RUN ls -la /mnt
#RUN ls -la /builds/shared/stormbringerdp/amaze-us/
#ADD /mnt/backend-latest.jar /backend/backend-latest.jar
#RUN find / -name *.jar
ENTRYPOINT ["java", "-jar", "/backend/backend-latest.jar"]
# Document that the service listens on port 8088.
EXPOSE 8088