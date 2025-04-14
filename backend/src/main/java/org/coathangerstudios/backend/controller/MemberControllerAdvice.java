package org.coathangerstudios.backend.controller;

import org.coathangerstudios.backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.UnsupportedMediaTypeException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MemberControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameOrEmailAddressAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUsernameOrEmailAddressIsAlreadyInUseException(UsernameOrEmailAddressAlreadyInUseException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MemberNotFoundWithGivenCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailAddressNotFoundException(MemberNotFoundWithGivenCredentialsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DatabaseSaveException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseSaveException(DatabaseSaveException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FileReadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFileReadException(FileReadException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NoSuchImageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNoSuchImageException(NoSuchImageException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UnsupportedMediaTypeException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String handleUnsupportedMediaTypeException(UnsupportedMediaTypeException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UnUploadedFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnUploadedFileException(UnUploadedFileException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMemberNotFoundException(MemberNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
