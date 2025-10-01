package com.mario.faceengine.model;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FaceRegistrationResponse extends BasicResponse {

    private String userId;
    private String requestId;
    private String type;
    private String createDate;
    @JsonIgnore
    private int[] encodingShape;
    @JsonIgnore
    private String faceEncodingBase64;

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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int[] getEncodingShape() {
        return encodingShape;
    }

    public void setEncodingShape(int[] encodingShape) {
        this.encodingShape = encodingShape;
    }

    public String getFaceEncodingBase64() {
        return faceEncodingBase64;
    }

    public void setFaceEncodingBase64(String faceEncodingBase64) {
        this.faceEncodingBase64 = faceEncodingBase64;
    }

    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("userId", userId);
        json.put("requestId", requestId);
        json.put("createDate", createDate);
        json.put("flow", type);
        if (encodingShape != null) {
            json.put("encodingShape", encodingShape);
        }
        if (faceEncodingBase64 != null) {
            json.put("faceEncodingBase64", faceEncodingBase64);
        }
        return json;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("userId", userId);
        json.put("requestId", requestId);
        json.put("type", type);
        json.put("createDate", createDate);
        json.put("code", getCode());
        json.put("message", getMessage());
        return json.toString();
    }
}
