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
}
