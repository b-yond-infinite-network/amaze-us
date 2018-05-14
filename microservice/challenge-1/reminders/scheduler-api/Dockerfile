FROM anapsix/alpine-java

ADD target/scheduler-api.jar scheduler-api.jar

EXPOSE 8080

CMD ["java","-jar","-Dspring.profiles.active=prod","-DXmx512m","-XX:MaxHeapFreeRatio=10","-XX:MinHeapFreeRatio=10","scheduler-api.jar"]
