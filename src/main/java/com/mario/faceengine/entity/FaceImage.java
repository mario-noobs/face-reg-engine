package com.mario.faceengine.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FACE_IMAGE")
public class FaceImage {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FILE_NAME")
    private String filename;

    @Column(name = "CREATE_DATE")
    private String createDate;

    @Column(name = "UPDATE_DATE")
    private String updateDate;

    @Column(name = "FLOW")
    private String flow;

    @Column(name = "REQUEST_ID")
    private String requestId;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
