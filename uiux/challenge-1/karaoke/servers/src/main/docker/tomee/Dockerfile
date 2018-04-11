FROM openjdk:8u162-jdk-stretch

USER root

ENV DOCKERIZE_VERSION v0.6.0
RUN wget --quiet https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN mkdir -p /opt
WORKDIR /opt
COPY tomee.tar.gz .
RUN tar xvzf tomee.tar.gz && rm tomee.tar.gz
RUN mv apache-* tomee
RUN rm -Rf /opt/tomee/webapps/*

WORKDIR /opt/tomee/conf/
COPY logging.properties .
COPY tomee.xml .

ENV JPDA_ADDRESS=8000
ENV JPDA_TRANSPORT=dt_socket
ENV JPDA_SUSPEND=n

EXPOSE 8080 8000

WORKDIR /opt/tomee/

COPY start.sh .
RUN chmod u+x start.sh

ENTRYPOINT ["/opt/tomee/start.sh"]
CMD ["run"]