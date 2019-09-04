package com.cathedral.building;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PostappApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostappApplication.class, args);
	}

}
