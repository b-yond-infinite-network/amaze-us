package com.challenge.test.automation.utils;

/**
 * Helper class to get Database properties based on specified profile and corresponding properties file 
 *
 */
public class DBConnector {
	
	private static final String DB_URL = "modulus.automation.test.db.url";
	private static final String DB_NAME = "modulus.automation.test.db.database";

	public String getDBName() {
		return PropertiesConfig.getProperty(DB_NAME);
	}
	
	public String getDBUrl() {
		return PropertiesConfig.getProperty(DB_URL);
	}
}
