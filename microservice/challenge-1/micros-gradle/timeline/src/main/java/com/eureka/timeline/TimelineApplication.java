package com.eureka.timeline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



/**
 * Timeline main app.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class TimelineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimelineApplication.class, args);
	}

}
