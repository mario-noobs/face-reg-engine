package com.mario.faceengine.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private String testUsername;
    private String validToken;

    @BeforeEach
    void setUp() {
        testUsername = "testuser";
        jwtUtil = new JwtUtil();
        validToken = jwtUtil.generateToken(testUsername);
    }

    @Test
    void testGenerateToken_ShouldReturnValidToken() {
        // When
        String token = jwtUtil.generateToken(testUsername);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void testExtractUsername_ShouldReturnCorrectUsername() {
        // When
        String extractedUsername = jwtUtil.extractUsername(validToken);

        // Then
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void testValidateToken_WithValidToken_ShouldReturnTrue() {
        // When
        boolean isValid = jwtUtil.validateToken(validToken, testUsername);

        // Then
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_WithWrongUsername_ShouldReturnFalse() {
        // When
        boolean isValid = jwtUtil.validateToken(validToken, "wronguser");

        // Then
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtUtil.validateToken(invalidToken, testUsername);
        });
    }

    @Test
    void testExtractUsername_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(Exception.class, () -> {
            jwtUtil.extractUsername(invalidToken);
        });
    }
}
