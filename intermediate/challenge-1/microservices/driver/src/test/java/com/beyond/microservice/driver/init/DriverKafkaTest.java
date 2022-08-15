package com.beyond.microservice.driver.init;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import com.beyond.microservice.driver.DriverApplication;
import com.beyond.microservice.driver.service.DriverService;
import com.beyond.microservice.driver.util.DriverTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DriverApplication.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
@ContextConfiguration(initializers = { DriverKafkaTest.Initializer.class })
@Slf4j
public class DriverKafkaTest {
    
    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer();
    
    @Autowired
    private KafkaListenerBean kafkaListenerBean;
    
    @Autowired
    private DriverService driverService;
    
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    
    static class Initializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers())
                              .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
    
    @Test
    public void orderCreatedSendsKafkaMassage() throws Exception {
        assertThat(kafkaContainer.isRunning(), is(true));
        for (int i = 0; i < 3; i++) {
            try {
                for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
                    .getListenerContainers()) {
                    ContainerTestUtils.waitForAssignment(messageListenerContainer, 1);
                }
            } catch (IllegalStateException ex) {
                log.warn("Waited unsuccessfully for Kafka assignments");
            }
        }
        int receivedBefore = kafkaListenerBean.getReceived();
        driverService.createDriver(DriverTestUtil.getDriver());
        int i = 0;
        while (kafkaListenerBean.getReceived() == receivedBefore && i < 10) {
            Thread.sleep(1000);
            i++;
        }
        assertThat(kafkaListenerBean.getReceived(), is(greaterThan(receivedBefore)));
    }
    
    
}
