package com.mario.faceengine.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {

    private static final Logger logger = Logger.getLogger(LogUtils.class.getName());

    public static void logRequest(String methodName, String requestData) {
        String logMessage = String.format("REQUEST | METHOD: %s | TIMESTAMP: %s | DATA: %s",
                methodName, System.currentTimeMillis(), requestData);
        logger.info(logMessage);
    }

    public static void logResponse(String methodName, String responseData) {
        String logMessage = String.format("RESPONSE | METHOD: %s | TIMESTAMP: %s | DATA: %s",
                methodName, System.currentTimeMillis(), responseData);
        logger.info(logMessage);
    }

    public static void logError(String methodName, String errorMessage) {
        String logMessage = String.format("ERROR | METHOD: %s | TIMESTAMP: %s | ERROR: %s",
                methodName, System.currentTimeMillis(), errorMessage);
        logger.log(Level.SEVERE, logMessage);
    }

    public static void logInfo(String methodName, String infoMessage) {
        String logMessage = String.format("INFO | METHOD: %s | TIMESTAMP: %s | INFO: %s",
                methodName, System.currentTimeMillis(), infoMessage);
        logger.info(logMessage);
    }
}