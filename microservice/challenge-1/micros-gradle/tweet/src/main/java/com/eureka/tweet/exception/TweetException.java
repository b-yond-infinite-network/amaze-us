package com.eureka.tweet.exception;


/**
 * Tweet exception.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
public class TweetException extends RuntimeException {

    public TweetException(String message) {
        super(message);
    }

    public TweetException(String message, Throwable cause) {
        super(message, cause);
    }

}

