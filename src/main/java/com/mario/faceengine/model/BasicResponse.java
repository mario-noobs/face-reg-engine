package com.mario.faceengine.model;

import org.json.JSONObject;

public class BasicResponse {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("code", code);
        json.put("message", message);

        return json;
    }
}
