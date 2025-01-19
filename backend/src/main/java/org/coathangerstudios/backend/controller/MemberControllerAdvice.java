package org.coathangerstudios.backend.controller;

import org.coathangerstudios.backend.exception.EmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.exception.EmailAddressNotFoundException;
import org.coathangerstudios.backend.exception.UsernameIsAlreadyInUseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MemberControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameIsAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUsernameIsAlreadyInUseException(UsernameIsAlreadyInUseException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmailAddressAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEmailAddressIsAlreadyInUseException(EmailAddressAlreadyInUseException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmailAddressNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmailAddressNotFoundException(EmailAddressNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsernameNotFoundException(UsernameNotFoundException e) {
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
