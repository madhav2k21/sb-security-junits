package com.techleads.app.exception;

public class DuplicateEmailIdException extends RuntimeException {
    public DuplicateEmailIdException(String message) {
        super(message);
    }
}
