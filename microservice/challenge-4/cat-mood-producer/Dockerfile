FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
COPY target/cat-mood-producer-0.0.1-SNAPSHOT.jar  cat-mood-producer.jar
ENTRYPOINT ["java","-jar","/cat-mood-producer.jar"]
