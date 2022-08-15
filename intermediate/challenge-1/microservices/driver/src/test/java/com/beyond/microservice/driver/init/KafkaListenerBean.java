package com.beyond.microservice.driver.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
@Slf4j
public class KafkaListenerBean {
    private int received = 0;
    
    @KafkaListener(topics = "order")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        log.info(cr.toString());
        log.info(cr.value().toString());
        received++;
    }
    
    public int getReceived() {
        return received;
    }
    
}
