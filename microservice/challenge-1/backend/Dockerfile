# Stage 1: build
FROM maven:3.6.3 AS BUILDER
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn -B -DskipTests clean package

# Stage 2: production
FROM openjdk:8-jdk-alpine
COPY --from=BUILDER /tmp/target/challenge1-backend-0.0.1-SNAPSHOT.jar /app.jar
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
