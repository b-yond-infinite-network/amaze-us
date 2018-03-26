package com.amertkara.am;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amertkara.am.ui.UIConfig;

@Import({
	UIConfig.class
})
@EnableAutoConfiguration
@ComponentScan
@Configuration
class ApplicationConfig {
}
