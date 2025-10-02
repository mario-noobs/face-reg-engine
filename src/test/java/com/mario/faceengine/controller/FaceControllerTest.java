package com.mario.faceengine.controller;

import com.mario.faceengine.handler.FaceHandler;
import com.mario.faceengine.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FaceControllerTest {

    @Mock
    private FaceHandler handler;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private FaceController faceController;

    private FaceRequest validRequest;
    private FaceRegistrationResponse mockResponse;

    @BeforeEach
    void setUp() {
        validRequest = new FaceRequest();
        validRequest.setRequestId("test-request-id");
        validRequest.setUserId("test-user");
        validRequest.setType(Flow.REGISTER.getFlow());
        validRequest.setImageBase64("dGVzdC1pbWFnZS1iYXNlNjQ=");

        mockResponse = new FaceRegistrationResponse();
        mockResponse.setCode("0000");
        mockResponse.setMessage("Success");
        mockResponse.setUserId("test-user");
        mockResponse.setRequestId("test-request-id");

        // Mock SecurityContext
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test-user");
    }

    @Test
    void testRegister_WithValidRequest_ShouldReturnSuccessResponse() throws Exception {
        // Given
        when(handler.registerIdentity(any(FaceRequest.class))).thenReturn(mockResponse);

        // When
        ResponseEntity<FaceRegistrationResponse> response = faceController.register(validRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("0000", response.getBody().getCode());
        assertEquals("Success", response.getBody().getMessage());
        assertEquals(Flow.REGISTER.getFlow(), validRequest.getType());
        assertEquals("test-user", validRequest.getUserId());
        verify(handler, times(1)).registerIdentity(any(FaceRequest.class));
    }

    @Test
    void testRegister_WhenHandlerThrowsException_ShouldReturnErrorResponse() throws Exception {
        // Given
        when(handler.registerIdentity(any(FaceRequest.class))).thenThrow(new RuntimeException("Test exception"));

        // When
        ResponseEntity<FaceRegistrationResponse> response = faceController.register(validRequest);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("E099", response.getBody().getCode());
        assertEquals("Unknown error occurred", response.getBody().getMessage());
    }

    @Test
    void testGetUserInfo_WithAuthentication_ShouldReturnUsername() {
        // When
        String userInfo = faceController.getUserInfo();

        // Then
        assertEquals("test-user", userInfo);
    }

}
