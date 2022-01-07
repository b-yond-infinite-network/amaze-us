package com.ms.reminder.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConfig {
	
	public OpenAPI springReminderOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Reminder App API")
	              .description("Reminder App V1 APIs")
	              .version("v1.0.0"));
	  }

}
