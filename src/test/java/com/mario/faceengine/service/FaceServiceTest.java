package com.mario.faceengine.service;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.helpers.HttpClient;
import com.mario.faceengine.model.FaceRegistrationRequest;
import com.mario.faceengine.model.FaceRegistrationResponse;
import com.mario.faceengine.model.FaceSearchResponse;
import com.mario.faceengine.model.Flow;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@WebMvcTest(FaceService.class)
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
            ).thenReturn(expectedResponse);

            FaceRegistrationResponse response = faceService.registerFace(params);
            actualResponse = response.toString();
        }
        assertEquals(expectedResponse, actualResponse);
    }
}
