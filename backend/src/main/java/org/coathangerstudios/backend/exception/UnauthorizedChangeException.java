package org.coathangerstudios.backend.exception;

public class UnauthorizedChangeException extends RuntimeException {
    public UnauthorizedChangeException() {
        super("Member is not authorized to change other members data");
    }
}

