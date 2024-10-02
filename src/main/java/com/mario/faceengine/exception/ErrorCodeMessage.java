package com.mario.faceengine.exception;

public enum ErrorCodeMessage {
    SUCCESS("0000", "Success"),
    USER_NOT_FOUND("E001", "User not found"),
    INVALID_INPUT("E002", "Invalid input provided"),
    DATABASE_ERROR("E003", "Database error occurred"),
    NETWORK_ERROR("E004", "Network error occurred"),
    UNKNOWN_ERROR("E099", "Unknown error occurred");

    private final String code;
    private final String message;

    ErrorCodeMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}