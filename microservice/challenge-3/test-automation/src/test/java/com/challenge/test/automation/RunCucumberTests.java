package com.challenge.test.automation;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Test runner for the features annotated with @IntegrationTest
 * @CucumberOptions
 * 		features scan the given directory
 * 		glue is a directory to find step Definition File
 * 		strict is stop the test execution if step definition is missed for the feature file
 * 		monochrome print in console more pretty
 *		plugin/pretty adds colors to output report
 *		plugin/html is for generating html report
 *		plugin/junit:target/cucumber.xml uses for Jenkin test reports
 *		plugin/junit:target/cucumber.json uses to render json reports to the user
 *	    tags uses to just run scenarios with the given tag
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = { "com.challenge.test.automation.glue" }
        , strict = true, monochrome = true
        , plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json", "junit:target/cucumber.xml" }
        , tags = { "not @Ignore" })
public class RunCucumberTests {
}
