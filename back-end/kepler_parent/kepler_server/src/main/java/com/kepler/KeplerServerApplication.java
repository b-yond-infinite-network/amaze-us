package com.kepler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KeplerServerApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KeplerServerApplication.class, args);
	}
}
