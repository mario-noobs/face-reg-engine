package com.mario.faceengine.service;

import com.mario.faceengine.model.*;
import org.springframework.stereotype.Service;

public interface FaceService {
    FaceRegistrationResponse registerFace(FaceRegistrationRequest request);
    FaceSearchResponse recognize(FaceSearchRequest request);
}
