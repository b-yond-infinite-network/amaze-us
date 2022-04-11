FROM openjdk
ARG JAR_FILE=target/challenge*.jar
COPY ${JAR_FILE} bus.jar
ENTRYPOINT ["java","-jar","/bus.jar"]