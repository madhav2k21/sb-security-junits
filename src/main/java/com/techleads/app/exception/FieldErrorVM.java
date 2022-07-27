package com.techleads.app.exception;

import lombok.Getter;

@Getter
public class FieldErrorVM {
    private String errorMessage;
    private String fieldName;
    private String rejectedValue;

    public FieldErrorVM(String errorMessage, String fieldName, String rejectedValue) {
        this.errorMessage = errorMessage;
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
    }
}
