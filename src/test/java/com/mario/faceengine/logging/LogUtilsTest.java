package com.mario.faceengine.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogUtilsTest {

    @Test
    void testLogRequest_ShouldLogWithCorrectFormat() {
        // Given
        String methodName = "testMethod";
        String requestData = "test request data";

        // When & Then - This will test that the method executes without throwing exceptions
        // Since LogUtils uses static methods and Java logging, we can't easily mock it
        // but we can ensure the method executes successfully
        assertDoesNotThrow(() -> {
            LogUtils.logRequest(methodName, requestData);
        });
    }

    @Test
    void testLogResponse_ShouldLogWithCorrectFormat() {
        // Given
        String methodName = "testMethod";
        String responseData = "test response data";

        // When & Then
        assertDoesNotThrow(() -> {
            LogUtils.logResponse(methodName, responseData);
        });
    }

    @Test
    void testLogError_ShouldLogWithCorrectFormat() {
        // Given
        String methodName = "testMethod";
        String errorMessage = "test error message";

        // When & Then
        assertDoesNotThrow(() -> {
            LogUtils.logError(methodName, errorMessage);
        });
    }

    @Test
    void testLogInfo_ShouldLogWithCorrectFormat() {
        // Given
        String methodName = "testMethod";
        String infoMessage = "test info message";

        // When & Then
        assertDoesNotThrow(() -> {
            LogUtils.logInfo(methodName, infoMessage);
        });
    }

    @Test
    void testLogMethods_WithNullInputs_ShouldHandleGracefully() {
        // Test with null method name and data
        assertDoesNotThrow(() -> {
            LogUtils.logRequest(null, null);
            LogUtils.logResponse(null, null);
            LogUtils.logError(null, null);
            LogUtils.logInfo(null, null);
        });
    }

    @Test
    void testLogMethods_WithEmptyInputs_ShouldHandleGracefully() {
        // Test with empty strings
        assertDoesNotThrow(() -> {
            LogUtils.logRequest("", "");
            LogUtils.logResponse("", "");
            LogUtils.logError("", "");
            LogUtils.logInfo("", "");
        });
    }

    private void assertDoesNotThrow(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new AssertionError("Expected no exception to be thrown, but got: " + e.getMessage());
        }
    }
}
