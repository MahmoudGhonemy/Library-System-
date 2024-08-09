package com.LibraryManagementSystem.exception;

public enum ErrorCode {
    // Define your error codes here
    BOOK_NOT_FOUND("BOOK NOT FOUND (404)"),
    PATRON_NOT_FOUND("PATRON NOT FOUND (404)"),
    NO_AVAILABLE_COPIES("THERE IS NO AVAILABLE COPIES OF THIS BOOK IN THE INVENTORY (400)");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
