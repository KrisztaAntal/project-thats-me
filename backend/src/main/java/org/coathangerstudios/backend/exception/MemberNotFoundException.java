package org.coathangerstudios.backend.exception;

import java.util.NoSuchElementException;

public class MemberNotFoundException extends NoSuchElementException {
    public MemberNotFoundException(String message) {super(message);}
}
