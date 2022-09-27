FROM bitnami/spark:3

# The spark image already have the needed jars, spark will also download what is needed if not found.
# RUN   mkdir /opt/bitnami/spark/ivy \
#       && curl https://repo1.maven.org/maven2/com/datastax/spark/spark-cassandra-connector_2.12/3.0.0/spark-cassandra-connector_2.12-3.0.0.jar --output /opt/bitnami/spark/jars/spark-cassandra-connector_2.12-3.0.0.jar \
#       && curl https://repo1.maven.org/maven2/org/apache/spark/spark-sql-kafka-0-10_2.12/3.0.0/spark-sql-kafka-0-10_2.12-3.0.0.jar --output /opt/bitnami/spark/jars/spark-sql-kafka-0-10_2.12-3.0.0.jar

WORKDIR /home

COPY requirements.txt .

USER root

RUN pip install -r ./requirements.txt

COPY . .

CMD ["python3", "start-job.py"]