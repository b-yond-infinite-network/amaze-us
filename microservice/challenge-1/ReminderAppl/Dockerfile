FROM openjdk:8-jdk-alpine as basebuilder

RUN mkdir -p /app/src
COPY . /app/src
WORKDIR /app/src
RUN ./mvnw clean package

FROM openjdk:8-jdk-alpine as dev

RUN apk update && apk upgrade && apk add --no-cache bash
RUN apk --no-cache add curl
COPY --from=basebuilder /app/src/target/reminder-app*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=dev"]

FROM openjdk:8-jdk-alpine as prod

RUN apk update && apk upgrade && apk add --no-cache bash
RUN apk --no-cache add curl
COPY --from=basebuilder /app/src/target/reminder-app*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar","--spring.profiles.active=prod"]
