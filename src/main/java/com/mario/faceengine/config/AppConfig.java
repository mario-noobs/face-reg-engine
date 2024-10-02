package com.mario.faceengine.config;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final int someTimeout;
    private String faceHostNameUrl;
    private String registerPath;
    private String recognizePath;

    // Private constructor to prevent instantiation
    private AppConfig() {
        this.someTimeout = Integer.parseInt(getEnvVar("APP_TIMEOUT", "30"));
        this.faceHostNameUrl = getEnvVar("FACE_HOST_NAME", "http://75.119.149.223:5000");
        this.registerPath = getEnvVar("REGISTER_PATH", "/face/create-identity");
        this.recognizePath = getEnvVar("RECOGNIZE_PATH", "/face/recognize");

    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    private String getEnvVar(String key) {
        return getEnvVar(key, null);
    }

    private String getEnvVar(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null) ? value : defaultValue;
    }

    public String getFaceHostNameUrl() {
        return faceHostNameUrl;
    }

    public String getRegisterPath() {
        return registerPath;
    }

    public String getRecognizePath() {
        return recognizePath;
    }

    public int getSomeTimeout() {
        return someTimeout;
    }
}
