FROM anapsix/alpine-java

ADD target/reminders-core-api.jar reminders-core-api.jar

EXPOSE 9000

CMD ["java","-jar","-Dspring.profiles.active=prod","-DXmx512m","-XX:MaxHeapFreeRatio=10","-XX:MinHeapFreeRatio=10","reminders-core-api.jar"]
