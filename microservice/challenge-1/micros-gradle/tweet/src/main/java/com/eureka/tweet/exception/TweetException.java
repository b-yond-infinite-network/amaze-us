package com.eureka.tweet.exception;


public class TweetException extends RuntimeException {

    public TweetException(String message) {
        super(message);
    }

    public TweetException(String message, Throwable cause) {
        super(message, cause);
    }

}

