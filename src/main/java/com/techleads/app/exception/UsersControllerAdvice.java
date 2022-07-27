package com.techleads.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class UsersControllerAdvice {


    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<StandardError> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        StandardError standardError = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateEmailIdException.class})
    public ResponseEntity<StandardError> handleUserDuplicateEmailIdException(DuplicateEmailIdException ex, HttpServletRequest request) {
        StandardError standardError = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> handleConstraintViolationException(MethodArgumentNotValidException ex, HttpServletRequest  request) {

        List<FieldErrorVM> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorVM(fieldError.getDefaultMessage(), fieldError.getField(), String.valueOf(fieldError.getRejectedValue())))
                .collect(Collectors.toList());

        StandardError standardError = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), ex.getObjectName(), request.getRequestURI(), fieldErrors);
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<StandardError> handleConstraintViolationException(Exception ex, HttpServletRequest request) {



        StandardError standardError = new StandardError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), String.valueOf(ex.fillInStackTrace()), request.getRequestURI());
        return new ResponseEntity<>(standardError, HttpStatus.BAD_REQUEST);
    }





}
