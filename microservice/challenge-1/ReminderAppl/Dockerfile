FROM openjdk:8-jdk-alpine as basebuilder

RUN mkdir -p /app/src
COPY . /app/src
WORKDIR /app/src
RUN ./mvnw clean package

FROM openjdk:8-jdk-alpine as final

RUN apk update && apk upgrade && apk add --no-cache bash
RUN apk --no-cache add curl
COPY --from=basebuilder /app/src/target/reminder-app-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]

