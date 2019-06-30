package com.challenge.test.automation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Hooks in Cucumber gives the possibility to execute some logics before and after each tags definded in 
 * feature classes  
 *
 */
public class Hooks {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Hooks.class);
	
	/**
	 * Runs before executing @RegressionTest
	 */
	@Before("@RegressionTest")
	public void beforeRegressionTest() {
		LOGGER.info("Before running regression Test cases");
	}
	
	/**
	 * Runs after executing @RegressionTest
	 */
	@After("@RegressionTest")
	public void afterRegressionTest() {
		LOGGER.info("After running regression Test cases");	
	}

}
