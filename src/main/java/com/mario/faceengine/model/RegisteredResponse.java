package com.mario.faceengine.model;

import org.json.JSONObject;

// Helper response class for registration status

public class RegisteredResponse {
  private boolean registered;
  private String requestId;

  public RegisteredResponse(boolean registered, String requestId) {
    this.registered = registered;
    this.requestId = requestId;
  }

  public boolean isRegistered() { return registered; }
  public void setRegistered(boolean registered) { this.registered = registered; }

  public String getRequestId() { return requestId; }
  public void setRequestId(String requestId) { this.requestId = requestId; }

  @Override
  public String toString() {
    JSONObject json = new JSONObject();
    json.put("registered", registered);
    json.put("requestId", requestId);
    return json.toString();
  }
}