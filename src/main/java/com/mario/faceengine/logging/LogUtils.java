package com.mario.faceengine.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {

    private static final Logger logger = Logger.getLogger(LogUtils.class.getName());

    public static void logRequest(String methodName, String requestData) {
        String logMessage = String.format("{\"type\":\"request\",\"method\":\"%s\",\"data\":%s}",
                methodName, requestData);
        logger.info(logMessage);
    }

    public static void logResponse(String methodName, String responseData, long executionTimeMs) {
        String logMessage = String.format("{\"type\":\"response\",\"method\":\"%s\",\"executionTimeMs\":%d,\"data\":%s}",
                methodName, executionTimeMs, responseData);
        logger.info(logMessage);
    }

    public static void logError(String methodName, String errorMessage) {
        String logMessage = String.format("{\"type\":\"error\",\"method\":\"%s\",\"timestamp\":%d,\"error\":\"%s\"}",
                methodName, System.currentTimeMillis(), errorMessage);
        logger.log(Level.SEVERE, logMessage);
    }

    public static void logInfo(String methodName, String infoMessage) {
        String logMessage = String.format("{\"type\":\"info\",\"method\":\"%s\",\"timestamp\":%d,\"info\":\"%s\"}",
                methodName, System.currentTimeMillis(), infoMessage);
        logger.info(logMessage);
    }
}