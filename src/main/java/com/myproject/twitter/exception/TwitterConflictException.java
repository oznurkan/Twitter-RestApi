package com.myproject.twitter.exception;

public class TwitterConflictException extends RuntimeException {
    public TwitterConflictException(String message) {
        super(message);
    }
}