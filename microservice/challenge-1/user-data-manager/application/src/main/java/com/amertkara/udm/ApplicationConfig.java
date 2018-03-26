package com.amertkara.udm;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amertkara.udm.api.ApiConfig;

@Import({
	ApiConfig.class
})
@EnableAutoConfiguration
@Configuration
class ApplicationConfig {
}
