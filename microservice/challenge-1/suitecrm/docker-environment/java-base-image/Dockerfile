FROM anapsix/alpine-java:8_server-jre

RUN apk add --no-cache su-exec

COPY entrypoint.sh /entrypoint.sh

RUN mkdir -p /usr/share/suitecrm/lib

VOLUME /usr/share/suitecrm /tmp

RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
