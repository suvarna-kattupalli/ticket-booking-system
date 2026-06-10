package com.Ticketing.ticketing.exception;

public class SeatAlreadyBookedException
        extends RuntimeException {

    public SeatAlreadyBookedException(String message) {
        super(message);
    }
}