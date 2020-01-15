package com.eureka.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Tweet main app
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class TweetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetApplication.class, args);
	}

}
