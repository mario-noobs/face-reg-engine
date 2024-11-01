package com.mario.faceengine.exception;

public class FaceException extends Exception {

    private static final long serialVersionUID = 1L;
    private final ErrorCodeMessage errorCode;

    public FaceException(ErrorCodeMessage errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode.getCode();
    }
    public String getErrorMessage() {
        return errorCode.getMessage();
    }
}