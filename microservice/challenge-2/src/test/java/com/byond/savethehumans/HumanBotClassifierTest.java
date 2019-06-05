package com.byond.savethehumans;


import com.byond.savethehumans.dispatch.Dispatcher;
import com.byond.savethehumans.controller.HumanBotClassifier;
import com.byond.savethehumans.model.ContentResponse;
import com.byond.savethehumans.model.UserReview;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.HashSet;


@RunWith(SpringRunner.class)
@WebMvcTest(value = HumanBotClassifier.class, secure = false)
public class HumanBotClassifierTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    Dispatcher mockDispatcher;

    /*
           This is a bot! She knows that she is a first generation bot. They are easy to detect. They use suspected words recklessly.
     */
    UserReview mockUserReviewBot1 = new UserReview("For sure I am a human. You can ask the service to make it sure...",  new HashSet<>(Arrays.asList("human", "amazing")));
    String jsonResponseBot1 = "{ \"review\": \"for sure i am a human  .   you can ask the service to make it sure  .    .    .  \", \"verbose\": \"  There is 1 of exactly matching suspected words.\", \"suspectedWords\": [\"amazing\", \"human\"],\"matchingSimilarWords\": [],\"matchingWords\": [\"human\"],\"isBot\": true }";
    ContentResponse mockContentResponseBot1 = new ContentResponse("for sure i am a human  .   you can ask the service to make it sure  .    .    .  ",  new HashSet<String>(Arrays.asList("human", "bot")), new HashSet<String>(), new HashSet<String>(Arrays.asList("human")), true);

    /*
           This is a advanced generation bot. She knows that we have a dictionary of suspected words. So she doesn't use them. Instead she use the similar words that we don't have in our dictionary.
           But our Deeplearning Bot Detection system, can detects this generation as well. It uses a real-time textual similarity detection.
     */
    UserReview mockUserReviewAdvancedBot1 = new UserReview("Awesome exciting outstanding you mean you can know me even if I use my latest brain?",  new HashSet<>(Arrays.asList("human", "amazing")));
    String jsonResponseAdvancedBot1= "{ \"review\": \"awesome exciting outstanding you mean you can know me even if i use my latest brain?\", \"verbose\": \"There is a small likelihood. But it is OK. But still I think he/she is a human being.\", \"suspectedWords\": [\"amazing\", \"human\"],\"matchingSimilarWords\": [\"awesome\"],\"matchingWords\": [],\"isBot\": false }";
    ContentResponse mockContentResponseAdvancedBot1 = new ContentResponse("awesome exciting outstanding you mean you can know me even if i use my latest brain?",  new HashSet<String>(Arrays.asList("amazing", "human")), new HashSet<String>(Arrays.asList("awesome")), new HashSet<String>(), false);

    /*
           This is a human being, at least so far we can say she is not a bot.
     */
    UserReview mockUserReviewHuman1 = new UserReview("I don't care what do you think about my persona. maybe I am robot. it is not important",  new HashSet<>(Arrays.asList("human", "amazing")));
    String jsonResponseHuman1= "{ \"review\": \"i don t care what do you think about my persona  .   maybe i am robot  .   it is not important\", \"verbose\": \"Seems the reviewer is a human being.\", \"suspectedWords\": [\"amazing\", \"human\"],\"matchingSimilarWords\": [],\"matchingWords\": [],\"isBot\": false }";
    ContentResponse mockContentResponseHuman1 = new ContentResponse("i don t care what do you think about my persona  .   maybe i am robot  .   it is not important",  new HashSet<String>(Arrays.asList("amazing", "human")), new HashSet<String>(), new HashSet<String>(), false);


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMeIamBot1() throws Exception {
        Mockito.when(
                mockDispatcher.investigate(mockUserReviewBot1)).thenReturn(mockContentResponseBot1);

        ObjectMapper Obj = new ObjectMapper();

        String jsonStr = Obj.writeValueAsString(mockUserReviewBot1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/smell").accept(MediaType.APPLICATION_JSON).content(jsonStr).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = jsonResponseBot1;

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }



    @Test
    public void testMeIamHuman() throws Exception {
        Mockito.when(
                mockDispatcher.investigate(mockUserReviewHuman1)).thenReturn(mockContentResponseHuman1);

        ObjectMapper Obj = new ObjectMapper();

        String jsonStr = Obj.writeValueAsString(mockUserReviewHuman1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/smell").accept(MediaType.APPLICATION_JSON).content(jsonStr).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = jsonResponseHuman1;

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }



    @Test
    public void testMeIamAdvancedBot() throws Exception {
        Mockito.when(
                mockDispatcher.investigate(mockUserReviewAdvancedBot1)).thenReturn(mockContentResponseAdvancedBot1);

        ObjectMapper Obj = new ObjectMapper();

        String jsonStr = Obj.writeValueAsString(mockUserReviewAdvancedBot1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/smell").accept(MediaType.APPLICATION_JSON).content(jsonStr).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        String expected = jsonResponseAdvancedBot1;

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}