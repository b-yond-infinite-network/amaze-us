package com.byond.savethehumans.dispatch;

import com.byond.savethehumans.learner.WordToVector;
import com.byond.savethehumans.model.ContentResponse;
import com.byond.savethehumans.model.UserReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Dispatcher holds the business logic of detecting bot/human reviewers.
 *
 */

@ComponentScan
@Component
@Service
public class Dispatcher {


    protected static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);


    @Autowired
    private  WordToVector w2v;

    @Value("${wordDistance}")
    private int wordDistance;

    @Value("${wordToVector.enabled}")
    private Boolean word2VecIsEnabled;


    /*  Loads the default suspected words such as Amazing and Human */
    @Value("#{'${defaultSuspectedWords}'.split(',')}")
    private  Set<String> defaultSuspectedWords;

    Set<String> allSuspectedWords = new HashSet<>();

    public ContentResponse investigate(UserReview c) throws Exception {

        if (!word2VecIsEnabled) {
            return null;
        }

        System.out.print("The incoming content is being processed...");


        if (c.getSuspectedWords()==null || c.getSuspectedWords().isEmpty()){
            c.setSuspectedWords(new HashSet<>());
            c.getSuspectedWords().addAll(defaultSuspectedWords);
        }

        ContentResponse response = new ContentResponse();

        response.setReview(c.getContent()).setSuspectedWords(c.getSuspectedWords());

        for (String word : c.getSuspectedWords()) {
            if (word2VecIsEnabled){
                if (!allSuspectedWords.contains(word.toLowerCase().trim())){
                    allSuspectedWords.addAll(w2v.getNearWords(word, wordDistance));
                }
            }
            if (c.getContent().toLowerCase().contains(word.toLowerCase())){
                allSuspectedWords.add(word.toLowerCase().trim());
            }
            allSuspectedWords.add(word.toLowerCase().trim());
        }


        StringTokenizer tokenizer = new StringTokenizer(c.getContent(), " ");


        int totalPhrases = 0;
        while (tokenizer.hasMoreElements()) {
            totalPhrases++;
            String w = tokenizer.nextToken();
            if (allSuspectedWords.contains(w.trim())){
                if (c.getSuspectedWords().contains(w)){
                    response.getExactMatchingSusptectedWords().add(w);
                } else {
                    response.getMatchingSuspectedWords().add(w);
                }
            }
        }

        if (response.getMatchingSuspectedWords().size()>=3){
            response.setBot(true).setVerbose(response.getVerbose() + " " + " Oh! I've found " + response.getMatchingSuspectedWords().size() + " of similar matching suspected words!");
        }
        logger.info(response.toString());

        if (response.getExactMatchingSusptectedWords().size()>=1){
            response.setBot(true).setVerbose(response.getVerbose() + " " + " There is " + response.getExactMatchingSusptectedWords().size() + " of exactly matching suspected words.");
        }

        logger.info(response.toString());

        if (!response.getBot()){
            if (response.getMatchingSuspectedWords().size()>0){
                response.setVerbose("There is a small likelihood. But it is OK. But still I think he/she is a human being.");
            } else {
                response.setVerbose("Seems the reviewer is a human being.");
            }

        }


        logger.info("Response is rendered: {}", response.toString());

        return response;
    }


}
