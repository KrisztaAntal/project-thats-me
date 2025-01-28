package org.coathangerstudios.backend.controller;

import org.coathangerstudios.backend.exception.UsernameOrEmailAddressAlreadyInUseException;
import org.coathangerstudios.backend.exception.MemberNotFoundWithGivenCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}
