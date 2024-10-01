package com.mario.faceengine.service;

import com.mario.faceengine.model.S3StorageRequest;
import com.mario.faceengine.model.S3StorageResponse;

public interface S3Service {
    S3StorageResponse saveImage(S3StorageRequest request);
}
