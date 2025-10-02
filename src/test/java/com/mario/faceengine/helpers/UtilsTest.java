package com.mario.faceengine.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void testHashSHA1_WithValidInput_ShouldReturnHashedString() {
        // Given
        String input = "teststring";
        
        // When
        String result = Utils.hashSHA1(input);
        
        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(40, result.length()); // SHA-1 produces 40 character hex string
        assertTrue(result.matches("[A-F0-9]+"));
    }

    @Test
    void testHashSHA1_WithEmptyString_ShouldReturnHashedEmptyString() {
        // Given
        String input = "";
        
        // When
        String result = Utils.hashSHA1(input);
        
        // Then
        assertNotNull(result);
        assertEquals(40, result.length());
    }

    @Test
    void testHashSHA1_WithNullInput_ShouldHandleGracefully() {
        // Given
        String input = null;
        
        // When & Then
        assertThrows(Exception.class, () -> {
            Utils.hashSHA1(input);
        });
    }

    @Test
    void testHashSHA1_SameInput_ShouldProduceSameHash() {
        // Given
        String input = "consistentinput";
        
        // When
        String result1 = Utils.hashSHA1(input);
        String result2 = Utils.hashSHA1(input);
        
        // Then
        assertEquals(result1, result2);
    }

    @Test
    void testHashSHA1_DifferentInputs_ShouldProduceDifferentHashes() {
        // Given
        String input1 = "input1";
        String input2 = "input2";
        
        // When
        String result1 = Utils.hashSHA1(input1);
        String result2 = Utils.hashSHA1(input2);
        
        // Then
        assertNotEquals(result1, result2);
    }
}
