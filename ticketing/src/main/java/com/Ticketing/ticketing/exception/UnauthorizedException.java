package com.Ticketing.ticketing.exception;

public class UnauthorizedException
        extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
