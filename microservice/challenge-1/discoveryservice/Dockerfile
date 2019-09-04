FROM openjdk:8-jdk-alpine
VOLUME /tmp
VOLUME /logs
ENV APP_FILE discoveryservice-0.0.1-SNAPSHOT.jar
ENV APP_HOME /usr/apps
EXPOSE 8761
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]