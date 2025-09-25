package com.mario.faceengine.model;

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
    return "DeleteIdentityRequest{" +
        "userId='" + userId + '\'' +
        ", algorithm='" + algorithm + '\'' +
        ", requestId='" + requestId + '\'' +
        '}';
  }
}
