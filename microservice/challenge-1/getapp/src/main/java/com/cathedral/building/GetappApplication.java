package com.cathedral.building;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetappApplication.class, args);
	}

}
