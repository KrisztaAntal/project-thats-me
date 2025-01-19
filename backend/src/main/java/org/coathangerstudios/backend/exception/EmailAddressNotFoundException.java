package org.coathangerstudios.backend.security;

import org.springframework.security.core.AuthenticationException;

public class EmailAddressNotFoundException extends AuthenticationException {
    public EmailAddressNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EmailAddressNotFoundException(String msg) {
        super(msg);
    }
}
