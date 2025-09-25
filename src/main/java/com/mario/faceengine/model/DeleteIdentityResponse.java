package com.mario.faceengine.model;

public class DeleteIdentityResponse extends BasicResponse {
    private String message;
    private String requestId;
    private String status;
    private String userId;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
