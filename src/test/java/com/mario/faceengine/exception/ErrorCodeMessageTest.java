package com.mario.faceengine.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeMessageTest {

    @Test
    void testErrorCodeMessage_AllEnumValues_ShouldHaveValidCodeAndMessage() {
        // Test SUCCESS
        assertEquals("0000", ErrorCodeMessage.SUCCESS.getCode());
        assertEquals("Success", ErrorCodeMessage.SUCCESS.getMessage());

        // Test USER_NOT_FOUND
        assertEquals("E001", ErrorCodeMessage.USER_NOT_FOUND.getCode());
        assertEquals("User not found", ErrorCodeMessage.USER_NOT_FOUND.getMessage());

        // Test NETWORK_ERROR
        assertEquals("E004", ErrorCodeMessage.NETWORK_ERROR.getCode());
        assertEquals("Network error occurred", ErrorCodeMessage.NETWORK_ERROR.getMessage());

        // Test UNKNOWN_ERROR
        assertEquals("E099", ErrorCodeMessage.UNKNOWN_ERROR.getCode());
        assertEquals("Unknown error occurred", ErrorCodeMessage.UNKNOWN_ERROR.getMessage());
    }

    @Test
    void testErrorCodeMessage_ToString_ShouldReturnValidJsonString() {
        // Given
        ErrorCodeMessage errorCode = ErrorCodeMessage.USER_NOT_FOUND;
        
        // When
        String jsonString = errorCode.toString();
        
        // Then
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"code\":\"E001\""));
        assertTrue(jsonString.contains("\"message\":\"User not found\""));
        assertTrue(jsonString.startsWith("{"));
        assertTrue(jsonString.endsWith("}"));
    }

    @Test
    void testErrorCodeMessage_JwtErrors_ShouldHaveCorrectCodes() {
        assertEquals("E021", ErrorCodeMessage.JWT_EXPIRY.getCode());
        assertEquals("E022", ErrorCodeMessage.JWT_INVALID_FORMAT.getCode());
        assertEquals("E023", ErrorCodeMessage.JWT_INVALID_SIGNATURE.getCode());
        assertEquals("E024", ErrorCodeMessage.JWT_INVALID_ERROR.getCode());
        assertEquals("E025", ErrorCodeMessage.INVALID_JWT.getCode());
    }

    @Test
    void testErrorCodeMessage_S3Errors_ShouldHaveCorrectCodes() {
        assertEquals("E010", ErrorCodeMessage.S3_CLIENT_ERROR.getCode());
        assertEquals("E010", ErrorCodeMessage.S3_BUCKET_ERROR.getCode());
        assertEquals("E011", ErrorCodeMessage.S3_UPLOAD_ERROR.getCode());
    }
}
