package com.mario.faceengine.config;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final int someTimeout;
    private final String faceHostNameUrl;
    private final String registerPath;
    private final String recognizePath;
    private final String s3Url;

    private final String s3Port;

    private final String s3Username;

    private final String s3Password;

    private final String s3Bucket;
    
    // Private constructor to prevent instantiation
    private AppConfig() {
        this.someTimeout = Integer.parseInt(getEnvVar("APP_TIMEOUT", "30"));
        this.faceHostNameUrl = getEnvVar("FACE_HOST_NAME", "http://75.119.149.223:5000");
        this.registerPath = getEnvVar("REGISTER_PATH", "/face/create-identity");
        this.recognizePath = getEnvVar("RECOGNIZE_PATH", "/face/recognize");
        this.s3Url = getEnvVar("S3_URL", "http://198.7.120.11");
        this.s3Port = getEnvVar("S3_PORT", "9000");
        this.s3Username = getEnvVar("S3_USERNAME", "admin");
        this.s3Password = getEnvVar("S3_PASSWORD", "123456789$");
        this.s3Bucket = getEnvVar("S3_BUCKET", "face-bucket");
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

    public String getS3Url() {
        return s3Url;
    }

    public int getS3Port() {
        return Integer.parseInt(s3Port);
    }

    public String getS3Username() {
        return s3Username;
    }

    public String getS3Password() {
        return s3Password;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }
}
