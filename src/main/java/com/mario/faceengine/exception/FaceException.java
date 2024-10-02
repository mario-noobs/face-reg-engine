package com.mario.faceengine.exception;

public class FaceException extends Exception {

    private static final long serialVersionUID = 1L;
    private final ErrorCodeMessage errorCode;

    public FaceException(ErrorCodeMessage errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public ErrorCodeMessage getErrorCode() {
        return errorCode;
    }
}