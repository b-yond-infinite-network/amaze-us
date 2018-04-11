FROM ubuntu:18.04 as exploded_war
RUN apt-get update && apt-get install unzip -y
RUN mkdir -p /opt/
WORKDIR /opt/
COPY ${project.build.finalName}.war ./a.war
RUN unzip a.war && rm a.war