package com.mario.faceengine.model;

import org.json.JSONObject;

public class DeleteIdentityRequest {
    private String userId;
    private String algorithm;
    private String requestId;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("userId", userId);
        json.put("algorithm", algorithm);
        json.put("requestId", requestId);
        return json.toString();
    }
}
