package com.techleads.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StandardError {

    private LocalDateTime timestamp;
    private int statusCode;
    private String objectName;
    private String path;
    List<FieldErrorVM> fieldErrors;

    public StandardError(LocalDateTime timestamp, int statusCode, String objectName, String path) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.objectName = objectName;
        this.path = path;
    }
}
