package com.mario.faceengine.model;

import org.json.JSONObject;

public class FaceRegistrationResponse extends BasicResponse {

    private String userId;
    private String type;
    private String createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("userId", userId);
        json.put("createDate", createDate);
        json.put("flow", type);

        return json;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
