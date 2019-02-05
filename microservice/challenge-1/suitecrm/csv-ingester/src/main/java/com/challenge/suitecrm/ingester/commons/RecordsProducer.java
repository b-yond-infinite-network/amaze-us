package com.challenge.suitecrm.ingester.commons;

import java.util.Properties;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


public interface RecordsProducer extends AutoCloseable{

    void send(final String topic, final SpecificRecord key, final SpecificRecord value);

    public class Kafka implements RecordsProducer{

        private final KafkaProducer<SpecificRecord, SpecificRecord> kafkaProducer;

        public Kafka(KafkaProducer<SpecificRecord, SpecificRecord> kafkaProducer) {
            this.kafkaProducer = kafkaProducer;
        }

        public Kafka(Properties settings){
            this(new KafkaProducer<>(settings));
        }

        @Override
        public void send(final String topic, final SpecificRecord key, final SpecificRecord value) {
            this.kafkaProducer.send(new ProducerRecord<>(topic, key, value), (recordMetadata, e) -> {
                if(e!=null){
                    throw new RuntimeException(e);//Aly: not cool :), but for just for simplify things
                }
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws Exception {
            if (this.kafkaProducer != null) {
                this.kafkaProducer.flush();
                this.kafkaProducer.close();
            }
        }
    }
}
