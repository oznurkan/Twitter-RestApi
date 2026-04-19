package com.myproject.twitter.exception;

import org.springframework.http.HttpStatus;

public class TwitterForbiddenException extends TwitterException{
    public TwitterForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
