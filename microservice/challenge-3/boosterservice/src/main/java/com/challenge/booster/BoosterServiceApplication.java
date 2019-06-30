package com.challenge.booster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class BoosterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoosterServiceApplication.class, args);
	}
}
