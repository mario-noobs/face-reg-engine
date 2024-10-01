package com.mario.faceengine.service;

import com.mario.faceengine.model.FaceRegistrationRequest;
import com.mario.faceengine.model.FaceRegistrationResponse;
import com.mario.faceengine.model.FaceRequest;
import com.mario.faceengine.model.FaceSearchResponse;
import org.springframework.stereotype.Service;

@Service
public class FaceServiceImpl implements FaceService {

    @Override
    public FaceRegistrationResponse registerFace(FaceRegistrationRequest request) {
        return null;
    }

    @Override
    public FaceSearchResponse recognize(FaceRequest request) {
        return null;
    }
}
