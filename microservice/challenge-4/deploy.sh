./spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
  --master local[*] \
  --conf "spark.streaming.kafka.consumer.cache.enabled=false" \
  --conf "spark.executor.extraJavaOptions=log4j-driver.properties" \
  --conf "spark.executor.extraJavaOptions=log4j-executor.properties" \
  --class com.byond.challenge4.Mysteries mysteries-of-the-cats.jar
