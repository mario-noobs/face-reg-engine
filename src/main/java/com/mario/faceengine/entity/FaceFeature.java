package com.mario.faceengine.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "FACE_FEATURE")
public class FaceFeature {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FEATURE")
    private String feature;

    @Column(name = "CREATE_DATE")
    private String createDate;

    @Column(name = "UPDATE_DATE")
    private String updateDate;

    @Column(name = "FLOW")
    private String flow;

    @Column(name = "ACTIVATE")
    private int activate;

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

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public int getActivate() {
        return activate;
    }

    public void setActivate(int activate) {
        this.activate = activate;
    }
}
