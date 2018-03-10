FROM openjdk:8-jre
ADD target/frontend.war /usr/share/service/frontend.war
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/service/frontend.war"]
EXPOSE 8080
