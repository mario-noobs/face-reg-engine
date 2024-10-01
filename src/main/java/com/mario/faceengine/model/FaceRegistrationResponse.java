package com.mario.faceengine.model;

public class FaceRegistrationResponse {

    private String userId;
    private String imageBase64;
    private String algDet;
    private String algReg;
    private String requestId;

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
}
