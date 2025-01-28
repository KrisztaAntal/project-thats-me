package org.coathangerstudios.backend.exception;

public class DatabaseSaveException extends RuntimeException{
    public DatabaseSaveException(String message){
        super(message);
    }
}
