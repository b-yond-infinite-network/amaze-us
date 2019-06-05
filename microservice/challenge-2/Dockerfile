FROM ibmjava:jre-alpine

LABEL maintainer="sedighi@gmail.com"

COPY ./target/save-the-humans-0.0.1-SNAPSHOT.jar /usr/src/byondch2/save-humans.jar

ADD ./data /usr/src/byondch2/data

ADD ./src/main/resources/application.properties /usr/src/byondch2/

WORKDIR /usr/src/byondch2

EXPOSE 8080

CMD ["java", "-Dlogging.level.root=INFO", "-Xmx2g" ,"-Djava.security.egd=file:/dev/./urandom", "-DbasePath=/usr/src/byondch2/", "-jar","save-humans.jar"]
