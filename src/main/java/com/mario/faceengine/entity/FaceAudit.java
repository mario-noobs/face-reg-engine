package com.mario.faceengine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FACE_AUDIT")
public class FaceAudit {

    @Id
    @Column(name = "ID")
    @GeneratedValue()
    private int id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROBABILITY")
    private String prob;

    @Column(name = "FACE_SEARCH")
    private String faceSearch;

    @Column(name = "CREATE_DATE")
    private String createDate;

    @Column(name = "UPDATE_DATE")
    private String updateDate;

    @Column(name = "FLOW")
    private String flow;

    @Column(name = "REQUEST_ID")
    private String requestId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "MESSAGE")
    private String message;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

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

    public String getFaceSearch() {
        return faceSearch;
    }

    public void setFaceSearch(String faceSearch) {
        this.faceSearch = faceSearch;
    }
}
