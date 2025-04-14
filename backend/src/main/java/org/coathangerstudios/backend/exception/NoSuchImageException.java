package org.coathangerstudios.backend.exception;

import org.hibernate.dialect.unique.CreateTableUniqueDelegate;

import java.util.NoSuchElementException;

public class NoSuchImageException extends NoSuchElementException {
    public NoSuchImageException(){super("No such image in the database");}
}
