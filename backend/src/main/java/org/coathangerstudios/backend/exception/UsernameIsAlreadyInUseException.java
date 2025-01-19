package org.coathangerstudios.backend.exception;

public class UsernameIsAlreadyInUseException extends RuntimeException{
    public UsernameIsAlreadyInUseException(String message){
        super(message);
    }
}
