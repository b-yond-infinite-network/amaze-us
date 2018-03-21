FROM openjdk:8u121-jdk-alpine

MAINTAINER Mert Kara <amertkara@gmail.com>

ENV JAR_NAME=application-1.0-SNAPSHOT.jar
ENV JAVA_OPTS="-Xms128M -Xmx128M -XX:MaxMetaspaceSize=64M"

COPY application/src/main/bash/entrypoint.sh /entrypoint.sh
COPY application/target/$JAR_NAME /$JAR_NAME
RUN apk update && apk add bash
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
