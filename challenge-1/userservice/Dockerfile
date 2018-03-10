FROM openjdk:8-jre
ADD target/userservice.war /usr/share/service/userservice.war
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/service/userservice.war"]
EXPOSE 8080