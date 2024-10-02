package com.mario.faceengine.model;

import com.mario.faceengine.helpers.Utils;
import org.json.JSONObject;

public class FaceSearchRequest {

    private String userId;
    private String imageBase64;
    private String algDet;
    private String algReg;
    private String requestId;
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getAlgDet() {
        return algDet;
    }

    public void setAlgDet(String algDet) {
        this.algDet = algDet;
    }

    public String getAlgReg() {
        return algReg;
    }

    public void setAlgReg(String algReg) {
        this.algReg = algReg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("userId", userId);
        json.put("algorithmDet", algDet);
        json.put("algorithmReg", algReg);
        json.put("imageBase64", imageBase64);
        json.put("requestId", requestId);
        json.put("flow", type);

        return json;
    }
}
