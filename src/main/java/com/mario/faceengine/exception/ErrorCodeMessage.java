package com.mario.faceengine.exception;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

public enum ErrorCodeMessage {
    SUCCESS("0000", "Success"),
    USER_NOT_FOUND("E001", "User not found"),
    INVALID_INPUT("E002", "Invalid input provided"),
    INVALID_BASE64("E0021", "Invalid input provided"),
    DATABASE_ERROR("E003", "Database error occurred"),
    NETWORK_ERROR("E004", "Network error occurred"),
    FILENAME_NOT_FOUND("E005", "Filename not exists"),
    S3_CLIENT_ERROR("E010", "S3 Client cannot connect"),
    S3_BUCKET_ERROR("E010", "S3 Bucket cannot connect"),
    S3_UPLOAD_ERROR("E011", "S3 upload failed"),
    JWT_EXPIRY("E021", "jwt token expired"),
    JWT_INVALID_FORMAT("E022", "jwt token format invalid"),
    JWT_INVALID_SIGNATURE("E023", "jwt token invalid signature"),
    JWT_INVALID_ERROR("E024", "jwt token invalid error"),
    INVALID_JWT("E025", "jwt token is null"),
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

    private JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}