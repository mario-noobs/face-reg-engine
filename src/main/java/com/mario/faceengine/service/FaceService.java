package com.mario.faceengine.service;

import com.mario.faceengine.model.FaceRegistrationRequest;
import com.mario.faceengine.model.FaceRegistrationResponse;
import com.mario.faceengine.model.FaceRequest;
import com.mario.faceengine.model.FaceSearchResponse;

public interface FaceService {
    FaceRegistrationResponse registerFace(FaceRegistrationRequest request);
    FaceSearchResponse recognize(FaceRequest request);
}
