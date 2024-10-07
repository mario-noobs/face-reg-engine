package com.mario.faceengine.service;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.helpers.HttpClient;
import com.mario.faceengine.model.*;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FaceServiceTest {
    private FaceRegistrationRequest mockFaceRegistrationRequest() {
        FaceRegistrationRequest request = new FaceRegistrationRequest();

        request.setType(Flow.REGISTER.getFlow());
        request.setRequestId("test-request-id");
        request.setUserId("test-user");
        request.setAlgReg("test-algo");
        request.setAlgDet("test-algo");
        request.setImageBase64("test-image");

        return request;
    }

    private FaceSearchRequest mockFaceRecognizeRequest() {
        FaceSearchRequest request = new FaceSearchRequest();

        request.setType(Flow.REGISTER.getFlow());
        request.setRequestId("test-request-id");
        request.setUserId("test-user");
        request.setAlgReg("test-algo");
        request.setAlgDet("test-algo");
        request.setImageBase64("test-image");

        return request;
    }

    @Mock
    private HttpClient httpClient;

    @Autowired
    FaceService faceService;

    @Test
    public void testFaceRegistration_Success() throws IOException {

        String expectedResponse = "{\"code\":\"0000\",\"message\":\"success\"}";
        String actualResponse = "";
        AppConfig appConfig = AppConfig.getInstance();
        FaceRegistrationRequest params = mockFaceRegistrationRequest();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(() ->
                    HttpClient.post(eq(appConfig.getFaceHostNameUrl() +
                            appConfig.getRegisterPath()), anyString())
            ).thenReturn(expectedResponse);

            FaceRegistrationResponse response = faceService.registerFace(params);
            actualResponse = response.toString();
        }
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testFaceRegistration_NetworkError() throws IOException {

        String expectedResponse = "{\"code\":\"E004\",\"message\":\"Network error occurred\"}";
        String actualResponse = "";
        AppConfig appConfig = AppConfig.getInstance();
        FaceRegistrationRequest params = mockFaceRegistrationRequest();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(() ->
                    HttpClient.post(eq(appConfig.getFaceHostNameUrl() +
                            appConfig.getRegisterPath()), anyString())
            ).thenThrow(new RuntimeException());

            FaceRegistrationResponse response = faceService.registerFace(params);
            actualResponse = response.toString();
        }
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testFaceSearch_Success() throws IOException {

        String expectedResponse = "{\"code\":\"0000\",\"message\":\"success\",\"data\":{}}";
        String actualResponseCode = "";
        String actualResponseMessage = "";
        AppConfig appConfig = AppConfig.getInstance();
        FaceSearchRequest params = mockFaceRecognizeRequest();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(() ->
                    HttpClient.post(eq(appConfig.getFaceHostNameUrl() +
                            appConfig.getRecognizePath()), anyString())
            ).thenReturn(expectedResponse);

            FaceSearchResponse response = faceService.recognize(params);
            actualResponseCode = response.getCode();
            actualResponseMessage = response.getMessage();

            JSONObject expectedJson = new JSONObject(expectedResponse);

            assertEquals(expectedJson.getString("code"), actualResponseCode);

            assertEquals(expectedJson.getString("message"), actualResponseMessage);
        }
    }

    @Test
    public void testFaceSearch_NetworkError() throws IOException {

        String expectedResponse = "{\"code\":\"E004\",\"message\":\"Network error occurred\",\"data\":{}}";
        String actualResponseCode = "";
        String actualResponseMessage = "";
        AppConfig appConfig = AppConfig.getInstance();
        FaceSearchRequest params = mockFaceRecognizeRequest();
        try (MockedStatic<HttpClient> mockedHttpClient = mockStatic(HttpClient.class)) {
            mockedHttpClient.when(() ->
                    HttpClient.post(eq(appConfig.getFaceHostNameUrl() +
                            appConfig.getRecognizePath()), anyString())
            ).thenThrow(new RuntimeException());

            FaceSearchResponse response = faceService.recognize(params);
            actualResponseCode = response.getCode();
            actualResponseMessage = response.getMessage();

            JSONObject expectedJson = new JSONObject(expectedResponse);

            assertEquals(expectedJson.getString("code"), actualResponseCode);

            assertEquals(expectedJson.getString("message"), actualResponseMessage);
        }
    }
}
