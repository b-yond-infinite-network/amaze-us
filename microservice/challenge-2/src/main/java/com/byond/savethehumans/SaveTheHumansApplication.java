package com.byond.savethehumans;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


/**

	Save the Humans, B-yond, Microservices, Challenge 2
    @Author Amir Sedighi

 */

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.byond"})
public class SaveTheHumansApplication implements CommandLineRunner {

	protected static final Logger logger = LoggerFactory.getLogger(SaveTheHumansApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SaveTheHumansApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		logger.info("Congratulation! Service is ready! Try: http://localhost:8080/swagger-ui.html");
	}



}
