package org.coathangerstudios.backend.exception;

public class EmailAddressAlreadyInUseException extends RuntimeException{
    public EmailAddressAlreadyInUseException(String message){
        super(message);
    }
}
