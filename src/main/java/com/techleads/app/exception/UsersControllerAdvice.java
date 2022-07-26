package com.techleads.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class UsersControllerAdvice {


    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        StandardError standardError = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

}
