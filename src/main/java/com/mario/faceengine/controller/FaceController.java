package com.mario.faceengine.controller;

import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.handler.FaceHandler;
import com.mario.faceengine.logging.LogUtils;
import com.mario.faceengine.model.*;
import com.mario.faceengine.util.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/face/v1/api")
public class FaceController {

    @Autowired
    private FaceHandler handler;

    public String getUserInfo() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

    @PostMapping("/register-identity")
    public ResponseEntity<FaceRegistrationResponse> register(@RequestBody FaceRequest request) {
        String method = "register";
        Timer timer = new Timer();
        timer.start();
        request.setType(Flow.REGISTER.getFlow());
        request.setUserId(getUserInfo());
        LogUtils.logRequest(method, request.toString());
        FaceRegistrationResponse response = new FaceRegistrationResponse();
        try {
            response = handler.registerIdentity(request);
        } catch (FaceException fe) {
            response.setCode(fe.getErrorCode());
            response.setMessage(fe.getErrorMessage());
        } catch (Exception e) {
            LogUtils.logError(method, e.toString());
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.UNKNOWN_ERROR.getMessage());
        }
        LogUtils.logResponse(method, response.toString(), timer.end());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/recognize-identity")
    public ResponseEntity<FaceSearchResponse> recognize(@RequestBody FaceRequest request) {
        String method = "recognize-identity";
        Timer timer = new Timer();
        timer.start();
        request.setUserId(getUserInfo());
        request.setType(Flow.RECOGNIZE.getFlow());
        LogUtils.logRequest(method, request.toString());
        FaceSearchResponse response = new FaceSearchResponse();
        try {
            response = handler.recognizeIdentity(request);
        } catch (FaceException fe) {
            response.setCode(fe.getErrorCode());
            response.setMessage(fe.getErrorMessage());
        } catch (Exception e) {
            LogUtils.logError(method, e.toString());
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.UNKNOWN_ERROR.getMessage());
        }
        LogUtils.logResponse(method, response.toString(), timer.end());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete-identity")
    public ResponseEntity<DeleteIdentityResponse> deleteIdentity(@RequestBody FaceRequest request) {
        String method = "delete-identity";
        Timer timer = new Timer();
        timer.start();
        request.setUserId(getUserInfo());
        request.setType(Flow.DELETE.getFlow());
        LogUtils.logRequest(method, request.toString());
        DeleteIdentityResponse response = handler.deleteIdentity(request);
        LogUtils.logResponse(method, response.toString(), timer.end());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/is-registered")
    public ResponseEntity<RegisteredResponse> isRegistered(@RequestParam String userId) {
        String method = "is-registered";
        Timer timer = new Timer();
        timer.start();
        FaceRequest request = new FaceRequest();
        request.setUserId(userId);
        LogUtils.logRequest(method, request.toString());
        boolean registered = handler.isUserRegistered(userId);
        RegisteredResponse response = new RegisteredResponse(registered, request.getRequestId());
        LogUtils.logResponse(method, response.toString(), timer.end());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
