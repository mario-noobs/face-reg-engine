package com.mario.faceengine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    private final int someTimeout;
    private final String faceHostNameUrl;
    private final String registerPath;
    private final String recognizePath;
    private final String deletePath;
    private final String s3Endpoint; // Use endpoint instead of url/port
    private final String s3Username;
    private final String s3Password;
    private final String s3Bucket;
    private final boolean s3AutoCreate;

    @Autowired
    public AppConfig(Environment env) {
        this.someTimeout = Integer.parseInt(getEnvVar(env, "APP_TIMEOUT", "30"));
        this.faceHostNameUrl = getEnvVar(env, "FACE_HOST_NAME", "http://face-regconition-service:5000");
        this.registerPath = getEnvVar(env, "REGISTER_PATH", "/face/create-identity");
        this.recognizePath = getEnvVar(env, "RECOGNIZE_PATH", "/face/recognize");
        this.deletePath = getEnvVar(env, "DELETE_PATH", "/face/delete-identity");
        this.s3Endpoint = getEnvVar(env, "S3_URL", "http://minio");
        this.s3Username = getEnvVar(env, "S3_USERNAME", "admin");
        this.s3Password = getEnvVar(env, "S3_PASSWORD", "123456789$");
        this.s3Bucket = getEnvVar(env, "S3_BUCKET", "face-bucket");
        this.s3AutoCreate = Boolean.parseBoolean(getEnvVar(env, "S3_AUTO_BUCKET", "false"));
    }

    private String getEnvVar(Environment env, String key, String defaultValue) {
        String value = env.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return (value != null) ? value : defaultValue;
    }

    public int getSomeTimeout() { return someTimeout; }
    public String getFaceHostNameUrl() { return faceHostNameUrl; }
    public String getRegisterPath() { return registerPath; }
    public String getRecognizePath() { return recognizePath; }
    public String getDeletePath() { return deletePath; }
    public String getS3Endpoint() { return s3Endpoint; }
    public String getS3Username() { return s3Username; }
    public String getS3Password() { return s3Password; }
    public String getS3Bucket() { return s3Bucket; }
    public boolean getS3AutoCreate() { return s3AutoCreate; }
}
