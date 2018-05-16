package com.byond.challenge4.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.byond.challenge4.cat.Cat;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Set;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatTweetingMoodActor extends AbstractActor {

    private static final Logger LOG = LoggerFactory.getLogger(CatTweetingMoodActor.class);

    private static final Config kafkaConfig = ConfigFactory.load().getConfig("kafka");

    private final Set<Cat> cats;
    private final Producer<String, String> producer;

    public static Props props(Set<Cat> cats, Producer<String, String> producer) {
        return Props.create(CatTweetingMoodActor.class, () -> new CatTweetingMoodActor(cats, producer));
    }

    public CatTweetingMoodActor(Set<Cat> cats, Producer<String, String> producer) {
        LOG.info("{} cats are now tweeting!", cats.size());
        this.cats = cats;
        this.producer = producer;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .matchEquals("TweetMood", s -> {
                cats.forEach(cat -> {
                    LOG.debug("{} has changed mood and is tweeting it !", cat);
                    producer.send(
                        new ProducerRecord<>(kafkaConfig.getString("topic"), cat.getName(),
                            String.valueOf(cat.getMood().getId())));
                });
            })
            .build();
    }
}
