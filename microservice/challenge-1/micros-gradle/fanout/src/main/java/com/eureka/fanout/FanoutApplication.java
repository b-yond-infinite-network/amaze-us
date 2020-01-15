package com.eureka.fanout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Fanout main app
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class FanoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanoutApplication.class, args);
	}

}
