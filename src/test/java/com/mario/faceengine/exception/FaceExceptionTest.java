package com.mario.faceengine.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaceExceptionTest {

    @Test
    void testFaceException_WithErrorCodeMessage_ShouldSetCorrectFields() {
        // Given
        ErrorCodeMessage errorCode = ErrorCodeMessage.USER_NOT_FOUND;
        
        // When
        FaceException exception = new FaceException(errorCode);
        
        // Then
        assertEquals("E001", exception.getErrorCode());
        assertEquals("User not found", exception.getErrorMessage());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testFaceException_WithDifferentErrorCodes_ShouldWorkCorrectly() {
        // Test with NETWORK_ERROR
        FaceException networkException = new FaceException(ErrorCodeMessage.NETWORK_ERROR);
        assertEquals("E004", networkException.getErrorCode());
        assertEquals("Network error occurred", networkException.getErrorMessage());

        // Test with DATABASE_ERROR
        FaceException dbException = new FaceException(ErrorCodeMessage.DATABASE_ERROR);
        assertEquals("E003", dbException.getErrorCode());
        assertEquals("Database error occurred", dbException.getErrorMessage());

        // Test with SUCCESS
        FaceException successException = new FaceException(ErrorCodeMessage.SUCCESS);
        assertEquals("0000", successException.getErrorCode());
        assertEquals("Success", successException.getErrorMessage());
    }

    @Test
    void testFaceException_IsInstanceOfException() {
        // Given
        FaceException exception = new FaceException(ErrorCodeMessage.UNKNOWN_ERROR);
        
        // Then
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
