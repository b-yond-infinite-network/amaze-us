FROM anapsix/alpine-java

ADD target/email-sender-api.jar email-sender-api.jar

EXPOSE 8080

CMD ["java","-jar","-Dspring.profiles.active=prod","-DXmx512m","-XX:MaxHeapFreeRatio=10","-XX:MinHeapFreeRatio=10","email-sender-api.jar"]
