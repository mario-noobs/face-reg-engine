package com.mario.faceengine.handler;

import com.mario.faceengine.model.FaceRequest;
import com.mario.faceengine.model.FaceResponse;

public class FaceHandler {

    private static FaceHandler faceHandler = null;

    public static FaceHandler getInstance() {
        if (faceHandler == null) {
            synchronized (FaceHandler.class) { // Step 4: Synchronize for thread safety
                if (faceHandler == null) { // Double-checked locking
                    faceHandler = new FaceHandler();
                }
            }
        }
        return faceHandler;
    }

    public FaceResponse registerIdentity(FaceRequest request) {
        FaceResponse response = new FaceResponse();

        return response;
    }

}
