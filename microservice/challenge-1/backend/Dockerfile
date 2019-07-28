FROM openjdk:12-alpine

LABEL maintainer="Igor Tcherniavski"

WORKDIR /backend

ADD . /backend

ENTRYPOINT ["java", "-jar", "/backend/backend-latest.jar"]

EXPOSE 8088