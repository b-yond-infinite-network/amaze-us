package com.byond.savethehumans.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;

/**
 *    The incoming user request object
 *
 */

@ApiModel(value="UserReview" , description = "This object carries the user's review, and the suspected word(s).")
public class UserReview {

    @ApiModelProperty(position = 1, value = "Content of the review",  required = true)
    @JsonProperty("review")
    private String content;

    @ApiModelProperty(position = 1, value = "Set the suspected words to override the default ones",  required = true)
    @JsonProperty("suspectedWords")
    private Set<String> suspectedWords;

    public String getContent() {
        return content;
    }

    public void setContent(String body) {
        this.content = body;
    }

    public Set<String> getSuspectedWords() {
        return suspectedWords;
    }

    public void setSuspectedWords(Set<String> suspectedWords) {
        this.suspectedWords = suspectedWords;
    }


    public UserReview(String content, Set<String> suspectedWords) {
        super();
        this.content = content;
        this.suspectedWords=suspectedWords;
    }

    @Override
    public String toString() {
        return String.format( "userReview [review=%s, suspectedWords=%s]", content, suspectedWords);
    }
}
