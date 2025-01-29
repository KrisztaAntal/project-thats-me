package org.coathangerstudios.backend.exception;

public class UsernameOrEmailAddressAlreadyInUseException extends RuntimeException{
    public UsernameOrEmailAddressAlreadyInUseException(){
        super("Username or email address is already in use, please choose another one");
    }
}
