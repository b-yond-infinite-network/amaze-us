package com.challenge.test.automation.utils;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.test.automation.Stepdefs;

 
/**
 * This class helps us to customize properties in each environment(local, staging or production ) 
 * for any further process like database parameters or message broker
 *
 */
public class PropertiesConfig {

	private static Logger LOGGER = LoggerFactory.getLogger(Stepdefs.class);
	private static final String PROFILE = "com.challenge.test.automation.profile";
	private static Properties appProps;

	static {
		appProps = new Properties();
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("automation.properties"));
			String profile = props.getProperty(PROFILE);
			appProps.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("env/" + profile + ".properties"));
		} catch (IOException e) {
			LOGGER.error("Something is wrong with the given profile, check if it exsits", e);
		}
	}

	public static String getProperty(String key) {
		return appProps.getProperty(key);
	}

}
