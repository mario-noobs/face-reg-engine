package com.mario.faceengine.model;

// Helper response class for registration status

public  class RegisteredResponse {
  private boolean registered;
  public RegisteredResponse(boolean registered) { this.registered = registered; }
  public boolean isRegistered() { return registered; }
  public void setRegistered(boolean registered) { this.registered = registered; }
}