package com.challenge.test.automation.glue;

import cucumber.api.java8.En;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * This class contains all scenario steps in feature files
 *
 */
public class StepsDef implements En {

    private static Logger LOGGER = LoggerFactory.getLogger(StepsDef.class);
    private static final String APPLICATION_JSON = "application/json";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpResponse response;
    private String responseAsString;
    private String objectId;

    public StepsDef() {

        Given("id after an API call is executed: GET {string}", (String url) -> {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", APPLICATION_JSON);
            HttpResponse httpResponse = httpClient.execute(request);
            String httpResponseAsString = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(httpResponseAsString);
            JSONArray jsonArray = new JSONArray(httpResponseAsString);
            if (!jsonArray.isEmpty()) {
                org.json.JSONObject myObject = jsonArray.getJSONObject(0);
                objectId = myObject.getString("_id");
            }
        });

        When("an API call is executed: POST {string} with body:", (String url, String requestBody) -> {
            HttpPost request = new HttpPost(url);
            StringEntity entity = new StringEntity(requestBody);
            request.addHeader("content-type", APPLICATION_JSON);
            request.setEntity(entity);
            response = httpClient.execute(request);
        });

        When("an API call is executed: GET {string}", (String url) -> {
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", APPLICATION_JSON);
            response = httpClient.execute(request);
        });

        When("an API call is executed: DELETE {string}", (String url) -> {
            url = objectId == null ? url : url.concat("/").concat(objectId);
            HttpDelete request = new HttpDelete(url);
            request.addHeader("content-type", APPLICATION_JSON);
            response = httpClient.execute(request);
        });

        Then("response status should be {int}", (Integer statusCode) -> {
            assertEquals(statusCode.longValue(), response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                responseAsString = EntityUtils.toString(response.getEntity());
                System.out.println(responseAsString);
            }
        });

        Then("response body contains {string}", (String message) -> {
            assertThat(responseAsString, containsString(message));
        });
    }
}
