package com.challenge.test.automation.utils;

/**
 * Helper class to get JMS properties based on specified profile and corresponding properties file 
 *
 */
public class JMSConnector {
	
	private static final String JMS_HOST = "com.challenge.test.jms.host";
	private static final String JMS_PORT = "com.challenge.test.jms.port";
	private static final String JMS_BOKER_NAME = "com.challenge.test.jms.broker.name";
	private static final String JMS_USERNAME = "com.challenge.test.jms.username";
	private static final String JMS_PASSWORD = "com.challenge.test.jms.password";
	
	public String getJMSHost() {
		return PropertiesConfig.getProperty(JMS_HOST);
	}
	
	public String getJMSPort() {
		return PropertiesConfig.getProperty(JMS_PORT);
	}
	
}
