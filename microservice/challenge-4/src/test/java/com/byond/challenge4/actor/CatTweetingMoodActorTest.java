package com.byond.challenge4.actor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.byond.challenge4.cat.Cat;
import java.util.HashSet;
import java.util.Set;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CatTweetingMoodActorTest {

    static ActorSystem system;

    @Mock
    Producer<String, String> producer;

    @Captor
    ArgumentCaptor<ProducerRecord<String, String>> producerRecordArgumentCaptor;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testThatChangeMoodIsSuccessfullyTweeted() {
        new TestKit(system) {
            {
                //Given
                final Cat cat = new Cat();
                final Set<Cat> cats = new HashSet<>();
                cats.add(cat);

                //And
                Props props = CatTweetingMoodActor.props(cats, producer);
                ActorRef catTweetingMoodActor = system.actorOf(props);

                //When
                catTweetingMoodActor.tell("TweetMood", getRef());

                //Then
                awaitAssert(() -> {
                    verify(producer).send(producerRecordArgumentCaptor.capture());
                    assertThat(producerRecordArgumentCaptor.getValue()).extracting("topic", "key", "value")
                        .containsExactly("cats-channel", cat.getName(), String.valueOf(cat.getMood().getId()));
                    return null;
                });
            };
        };
    }

}
