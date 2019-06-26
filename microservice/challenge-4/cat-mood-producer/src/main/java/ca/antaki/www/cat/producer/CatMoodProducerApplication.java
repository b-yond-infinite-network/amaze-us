package ca.antaki.www.cat.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CatMoodProducerApplication {
   private static final Logger LOG = LoggerFactory.getLogger(CatMoodProducerApplication.class);


	public static void main(String[] args) {
		LOG.info("Running cat-mood-producer");
		SpringApplication.run(CatMoodProducerApplication.class, args);
	}

}
