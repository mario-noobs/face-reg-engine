package com.mario.faceengine.model;

import org.json.JSONObject;

import java.util.Map;

public class FaceSearchResponse extends BasicResponse {
    private Map<String, Object> searchData;
    private String requestId;
    private String userId;

    public Map<String, Object> getSearchData() {
        return searchData;
    }

    public void setSearchData(JSONObject searchData) {
        this.searchData = searchData.toMap();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JSONObject toJson() {

        JSONObject json = super.toJson();;

        json.put("userId", userId);
        json.put("requestId", requestId);
        json.put("searchData", searchData);

        return json;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
