package com.byond.savethehumans.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *   Response type
 *
 */

@ApiModel(value="ContentResponse" , description = "This object brings you the response.")
public class ContentResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isBot = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String review;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String verbose = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> suspectedWords = new HashSet<String>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> matchingSuspectedWords = new HashSet<String>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> exactMatchingSusptectedWords = new HashSet<String>();



    public ContentResponse(String review, Set<String> suspectedWords, Set<String> matchingWords, Set<String> exactMatchingWords, Boolean isBot) {
        super();
        this.review = review;
        this.suspectedWords = suspectedWords;
        this.matchingSuspectedWords = matchingWords;
        this.exactMatchingSusptectedWords = exactMatchingWords;
        this.isBot = isBot;
    }

    public ContentResponse() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContentResponse other = (ContentResponse) obj;
        return true;
    }

    @JsonProperty("review")
    public String getReview() {
        return review;
    }


    public ContentResponse setReview(String review) {
        this.review = review;
        return this;
    }

    @JsonProperty("verbose")
    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }


    @JsonProperty("suspectedWords")
    public Set<String> getSuspectedWords() {
        return suspectedWords;
    }

    public ContentResponse  setSuspectedWords(Set<String> suspectedWords) {
        this.suspectedWords = suspectedWords;
        return this;
    }

    @JsonProperty("matchingSimilarWords")
    public Set<String> getMatchingSuspectedWords() {
        return matchingSuspectedWords;
    }

    public ContentResponse setMatchingSuspectedWords(Set<String> matchingSuspectedWords) {
        this.matchingSuspectedWords = matchingSuspectedWords;
        return this;
    }


    @JsonProperty("matchingWords")
    public  Set<String> getExactMatchingSusptectedWords() {
        return exactMatchingSusptectedWords;
    }

    public ContentResponse setExactMatchingSusptectedWords(Set<String> exactMatchingSusptectedWords) {
        this.exactMatchingSusptectedWords = exactMatchingSusptectedWords;
        return this;
    }

    @JsonProperty("isBot")
    public Boolean getBot() {
        return isBot;
    }


    public ContentResponse setBot(Boolean bot) {
        isBot = bot;
        return this;
    }

    @Override
    public String toString() {
        return String.format( "contentResponse [review=%s, suspectedWords=%s ]", review, suspectedWords);
    }



}
