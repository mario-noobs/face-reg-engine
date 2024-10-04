package com.mario.faceengine.model;

import org.json.JSONObject;

import java.util.Map;

public class FaceSearchResponse extends BasicResponse {
    private Map<String, Object> searchData;
    private String requestId;
    private String userId;
    private String flow;

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

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public JSONObject toJson() {

        JSONObject json = super.toJson();;

        json.put("userId", userId);
        json.put("requestId", requestId);
        json.put("searchData", searchData);
        json.put("flow", flow);

        return json;
    }

    @Override
    public String toString() {
        return toJson().toString();
    }
}
