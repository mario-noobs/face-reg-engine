package com.mario.faceengine.model;

import com.mario.faceengine.helpers.Utils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FaceRequest {

    private static int sequenceNumber = 0;

    private String requestId = generateRequestId();
    private String imageBase64;
    private String userId;
    private String type;
    private final String appName = "faceengine";
    private String filename;

    private synchronized int getNextSequenceNumber() {
        return ++sequenceNumber; // Increment the sequence number
    }

    private String generateRequestId() {
        int number = getNextSequenceNumber();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return String.format("%s_%ss%d_%s", "transaction", appName, number, timestamp);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

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

    public Boolean isValidReq() {
        return this.requestId != null
                && this.imageBase64 != null
                && this.userId != null
                && this.type != null;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("userId", userId);
        json.put("requestId", requestId);
        json.put("imageBase64", Utils.hashSHA1(imageBase64));
        json.put("requestId", requestId);
        json.put("flow", type);

        return json;
    }
}
