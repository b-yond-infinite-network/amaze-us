package com.byond.savethehumans.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import com.byond.savethehumans.dispatch.Dispatcher;
import com.byond.savethehumans.model.ContentResponse;
import com.byond.savethehumans.model.UserReview;
import com.byond.savethehumans.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Human/Bot Classifier", description = "Call our WordEmbedding version of Human/Bot Classifier!")
public class HumanBotClassifier {
    protected static final Logger logger = LoggerFactory.getLogger(HumanBotClassifier.class);


    @Autowired
    Dispatcher dispatcher;


    @ApiOperation(value = "I smell users reviews to classify them into Human or Bot categories." , response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    @PostMapping(value = "/smell")
    public ResponseEntity<ContentResponse> smell(@RequestBody UserReview userContent) throws Exception {

        // We reject to process, the very short reviews.
        if (userContent.getContent()==null || userContent.getContent().trim().length() <= 4) {
            logger.error("Need more textual data in review field.");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Need more textual data in review field.");
        }

        try {

            // Applying some minor normalization on the review content.
            userContent.setContent(FileUtil.simpleRefine(userContent.getContent()).toLowerCase());

            UserReview c  = new UserReview(userContent.getContent(), userContent.getSuspectedWords());

            //c.setContent(userContent.getContent());

            logger.info(c.toString());

            ContentResponse response = dispatcher.investigate(c);
            if (response==null) {
                logger.info("Service is still training. Please try again later...");
                return new ResponseEntity<ContentResponse>(response, HttpStatus.SERVICE_UNAVAILABLE);
            }
            logger.info("Response from Dispatcher: {}" , response.toString());
            return new ResponseEntity<ContentResponse>(response, HttpStatus.OK);

        } catch (Exception ex){
            logger.error("An error occurred inside the controller. {}" , ex.getMessage() );
            return new ResponseEntity<ContentResponse>(new ContentResponse(), HttpStatus.CONFLICT);
        }
    }
}
