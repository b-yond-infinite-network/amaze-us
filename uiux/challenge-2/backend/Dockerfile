FROM openjdk:11-jre

RUN mkdir -p /app
WORKDIR /app

EXPOSE 8103

COPY build/libs/colony-kepler.jar /app

ENV PROFILE=DEV

CMD java -Dspring.profiles.active=$PROFILE $JVM_OPTIONS -jar /app/colony-kepler.jar


HEALTHCHECK CMD wget --quiet -O- http://localhost:8103/health || exit 1
