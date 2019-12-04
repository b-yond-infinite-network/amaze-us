package com.eureka.fanout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FanoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanoutApplication.class, args);
	}

}
