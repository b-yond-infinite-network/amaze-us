package com.challenge.test.automation;

import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.test.automation.utils.RequestSender;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class contains all scenario steps in feature files
 *
 */
public class Stepdefs {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Stepdefs.class);
	private String url;
	private String response;
	
	/**
	 * Set a URL to send a request
	 * @param url
	 * @throws Throwable
	 */
	@Given("^Request URL is \"([^\"]*)\"$")
	public void request_URL_is(String url) throws Throwable {
		LOGGER.info("Set a request url to '{}'", url);
	    this.url = url;
	}

	/**
	 * Send HTTP request without body
	 * @param requestMethod
	 * @throws Throwable
	 */
	@When("^Sending a \"([^\"]*)\" request$")
	public void sending_a_request(String requestMethod) throws Throwable {
		LOGGER.info("Sending a '{}' request to '{}' url", requestMethod, url);
		response = RequestSender.Send(url, requestMethod);
	}
	
	/**
	 * Send HTTP request with body
	 * @param requestMethod
	 * @param payload
	 * @throws Throwable
	 */
	@When("^Sending a \"([^\"]*)\" request with payload \"([^\"]*)\"$")
	public void sending_a_request_with_payload(String requestMethod, String payload) throws Throwable {
		LOGGER.info("Sending a '{}' request to '{}' url with payload, {}", requestMethod, url, payload);
		response = RequestSender.Send(url, requestMethod, payload);
	}

	/**
	 * Asserts expected and actual results
	 * @param expectedResponse
	 * @throws Throwable
	 */
	@Then("^Expected response is \"([^\"]*)\"")
	public void expected_response_is_with_status_code(String expectedResponse) throws Throwable {
		LOGGER.info("Asserting expected response -EXPECTED- {} -EXPECTED- with actual, -ACTUAL- {} -ACTUAL-"
				, expectedResponse, response);
		assertEquals(expectedResponse, response);
	}

}
