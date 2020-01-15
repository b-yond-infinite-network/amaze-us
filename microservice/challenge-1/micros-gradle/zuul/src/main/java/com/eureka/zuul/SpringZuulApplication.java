package com.eureka.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * Zuul main app
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@SpringBootApplication
@EnableEurekaClient 	// It acts as a eureka client
@EnableZuulProxy		// Enable Zuul

public class SpringZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringZuulApplication.class, args);
	}
}