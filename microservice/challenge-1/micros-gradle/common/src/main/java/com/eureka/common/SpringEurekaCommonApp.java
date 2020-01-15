package com.eureka.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Common main app.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringEurekaCommonApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaCommonApp.class, args);
	}
}
