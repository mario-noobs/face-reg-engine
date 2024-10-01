package com.mario.faceengine.controller;

import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.handler.FaceHandler;
import com.mario.faceengine.model.FaceRequest;
import com.mario.faceengine.model.FaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/face/v1/api")
public class FaceController {

    @PostMapping("/register-identity")
    public ResponseEntity<FaceResponse> register(@RequestBody FaceRequest request) {

        FaceResponse response = new FaceResponse();
        try {
            FaceHandler handler = FaceHandler.getInstance();
            response = handler.registerIdentity(request);
        } catch (FaceException fe) {
            response.setCode(fe.getErrorCode().getCode());
            response.setMessage(fe.getErrorCode().getMessage());
        } catch (Exception e) {
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.UNKNOWN_ERROR.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
