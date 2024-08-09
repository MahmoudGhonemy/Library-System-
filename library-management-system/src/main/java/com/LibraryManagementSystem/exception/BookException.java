package com.LibraryManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BookException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Long> parametersMap;

    public BookException(ErrorCode errorCode, Map<String, Long> parametersMap) {
        super(errorCode.getCode()); // Pass the error code to the superclass constructor
        this.errorCode = errorCode;
        this.parametersMap = parametersMap;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, Long> getParametersMap() {
        return parametersMap;
    }
}
