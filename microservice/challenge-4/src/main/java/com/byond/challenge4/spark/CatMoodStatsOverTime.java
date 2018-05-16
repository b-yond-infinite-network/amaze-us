package com.byond.challenge4.spark;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.first;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.mean;
import static org.apache.spark.sql.functions.variance;
import static org.apache.spark.sql.functions.window;

import com.byond.challenge4.actor.CatTweetingMoodActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.byond.challenge4.cat.Cat;
import com.byond.challenge4.cat.Mood;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.time.Duration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.log4j.Level;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatMoodStatsOverTime {

    private static final Logger LOG = LoggerFactory.getLogger(CatMoodStatsOverTime.class);
    private static final Duration TWEET_MOOD_FREQUENCY = Duration.ofSeconds(27);

    private static final Config kafkaConfig = ConfigFactory.load().getConfig("kafka");

    static {
        //Adjust Spark log level to be less verbose
        org.apache.log4j.Logger.getLogger("org").setLevel(Level.OFF);
        org.apache.log4j.Logger.getLogger("akka").setLevel(Level.OFF);
    }

    public static void main(String[] args) throws StreamingQueryException {

        if (args.length < 1) {
            LOG.error(
                "Usage: CatMoodStatsOverTime <number-of-cats> where <number-of-cats> is the number of cats that will be tweeting");
            System.exit(1);
        }

        final int numberOfCats = Integer.parseInt(args[0]);

        //Initialize Kafka producer
        final Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConfig.getString("bootstrap-servers"));
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        //Generate cats
        final Set<Cat> cats = new HashSet<>();
        IntStream.range(0, numberOfCats).forEach(number -> cats.add(new Cat()));
        LOG.info("Generated {} cats !", cats.size());

        //Create an actor based system with a scheduler that will make the cats tweet every 27 seconds
        ActorSystem actorSystem = ActorSystem.create("Lazy-cats-tweeting-all-day-long");
        ActorRef catTweetingMoodActor = actorSystem.actorOf(CatTweetingMoodActor.props(cats, producer));
        actorSystem.scheduler().schedule(Duration.ZERO,
            TWEET_MOOD_FREQUENCY, catTweetingMoodActor, "TweetMood", actorSystem.dispatcher(), null);

        //Spark time
        SparkSession spark = SparkSession.builder().appName("Cat Mood Extractor").config("master", "local[*]")
            .getOrCreate();

        Dataset<Row> catMoods = getAllTweetedCatMoodsWithTimestamp(spark);
        LOG.info("Formatting the messages received from Kafka... Here is a sample :");
        catMoods.show(false);

        Dataset<Row> overallCatMood = getOverallCatMoodByCountDesc(catMoods);
        LOG.info("Overall mood stats since cats started tweeting");
        overallCatMood.show();

        Dataset<Row> trendingCatMood = getOverallTrendingCatMood(overallCatMood);
        LOG.info("The overall trending cat mood as of now is {}", Mood
            .fromId(Integer.valueOf((String) trendingCatMood.head().get(0))));

        //Trending cat mod in the last 2 minutes
        Dataset<Row> catMoodsWith2minWindow = getCatMoodsWith2minWindow(catMoods);
        LOG.info("The trending cat mod in the last 2 minutes is {}", Mood
            .fromId(Integer.valueOf(String.valueOf(catMoodsWith2minWindow.head().get(1)))));

        //A few stats with the mood counts over the 2min window data : average, mean, variance
        catMoodsWith2minWindow.withColumn("mean", mean(col("count")).over(Window.partitionBy("mood"))).show(false);
        catMoodsWith2minWindow.withColumn("avg", avg(col("count")).over(Window.partitionBy("mood"))).show(false);
        catMoodsWith2minWindow.withColumn("variance", variance(col("count")).over(Window.partitionBy("mood")))
            .show(false);

        //Reading from Kafka stream - almost - "live" for fun!
        monitorLast2minMoodTrendFromStream(spark);

        spark.stop();
    }

    private static Dataset<Row> getCatMoodsWith2minWindow(Dataset<Row> catMoods) {
        Dataset<Row> catMoodsBy2minWindow = catMoods.groupBy(window(col("timestamp"), "2 minute"), col("mood"))
            .count().orderBy(col("window").desc(), col("count").desc()).cache();
        LOG.info("Mood counters from the last 2 minutes");
        catMoodsBy2minWindow.show(false);
        return catMoodsBy2minWindow;
    }

    private static Dataset<Row> getAllTweetedCatMoodsWithTimestamp(SparkSession spark) {
        return spark.read().format("kafka")
            .option("kafka.bootstrap.servers", kafkaConfig.getString("bootstrap-servers"))
            .option("subscribe", kafkaConfig.getString("topic"))
            .option("startingOffsets", "earliest")
            .load().selectExpr("CAST(key AS STRING) as name", "CAST(value AS STRING) as mood",
                "CAST(timestamp AS TIMESTAMP) as timestamp").cache();
    }

    private static Dataset<Row> getOverallCatMoodByCountDesc(Dataset<Row> catMoods) {
        //Overall counters by mood since cats started tweeting
        return catMoods.groupBy("mood").count().orderBy(col("count").desc()).cache();
    }

    private static Dataset<Row> getOverallTrendingCatMood(Dataset<Row> overralCatMood) {
        Dataset<Row> trendingCatMood = overralCatMood.agg(first("mood"), max("count")).cache();
        LOG.info("Isolating the mood that has the max(count)...");
        trendingCatMood.show();
        return trendingCatMood;
    }

    private static void monitorLast2minMoodTrendFromStream(SparkSession spark) throws StreamingQueryException {
        LOG.info(
            "Now constantly monitoring the last 2 minutes trend by reading directly from the Kakfka stream... and outputting results in a console sink...");
        Dataset<Row> catMoodsStream = spark.readStream().format("kafka")
            .option("kafka.bootstrap.servers", kafkaConfig.getString("bootstrap-servers"))
            .option("subscribe", kafkaConfig.getString("topic"))
            .option("startingOffsets", "earliest")
            .load().selectExpr("CAST(key AS STRING) as name", "CAST(value AS STRING) as mood",
                "CAST(timestamp AS TIMESTAMP) as timestamp");

        Dataset<Row> moodCountsPerCatLastHour = catMoodsStream
            .groupBy(window(col("timestamp"), "2 minute"), col("mood"))
            .count().orderBy(col("window").desc(), col("count").desc());

        StreamingQuery queryMoodCountsFromLast2Min = moodCountsPerCatLastHour.writeStream().option("truncate", false)
            .format("console")
            .outputMode(OutputMode.Complete()).start();

        queryMoodCountsFromLast2Min.awaitTermination();
    }

}
