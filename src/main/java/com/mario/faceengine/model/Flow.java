package com.mario.faceengine.model;

public enum Flow {
    REGISTER("REGISTER"),
    MATCHING("MATCHING"),
    RECOGNIZE("RECOGNIZE"),
    DELETE("DELETE"),
    NONE("NONE");

    private final String flow;

    Flow(String flow) {
        this.flow = flow;
    }

    public String getFlow() {
        return flow;
    }
}
