package org.coathangerstudios.backend.exception;

import org.springframework.security.core.AuthenticationException;

public class MemberNotFoundWithGivenCredentialsException extends AuthenticationException {
    public MemberNotFoundWithGivenCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MemberNotFoundWithGivenCredentialsException() {
        super("User cannot be found with given credentials.");
    }
}
