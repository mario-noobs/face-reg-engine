package com.mario.faceengine.handler;

import com.mario.faceengine.entity.FaceImage;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.model.*;
import com.mario.faceengine.repository.FaceImageRepository;
import com.mario.faceengine.service.FaceService;
import com.mario.faceengine.service.FaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class FaceHandler {

    @Autowired
    FaceService faceService;

    @Autowired
    FaceImageRepository faceImageRepository;

    public FaceResponse registerIdentity(FaceRequest request) throws FaceException {
        FaceResponse response = new FaceResponse();

        if (!request.isValidReq()) {
            throw new FaceException(ErrorCodeMessage.INVALID_INPUT);
        }

        FaceRegistrationRequest faceRegistrationRequest = new FaceRegistrationRequest();
        faceRegistrationRequest.setRequestId(request.getRequestId());
        faceRegistrationRequest.setUserId(request.getUserId());
        faceRegistrationRequest.setAlgReg("mobilenet");
        faceRegistrationRequest.setAlgDet("mobilenet");
        faceRegistrationRequest.setImageBase64(request.getImageBase64());
        faceRegistrationRequest.setType(request.getType());

        try {
            FaceRegistrationResponse faceRegistrationResponse = this.faceService.registerFace(faceRegistrationRequest);

            if (!faceRegistrationResponse.getCode().equals(ErrorCodeMessage.SUCCESS.getCode())) {
                response.setCode(faceRegistrationResponse.getCode());
                response.setMessage(faceRegistrationResponse.getMessage());
            } else {
                response.setCode(ErrorCodeMessage.SUCCESS.getCode());
                response.setMessage(ErrorCodeMessage.SUCCESS.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.UNKNOWN_ERROR.getMessage());
        }

        return response;
    }

    public FaceSearchResponse recognizeIdentity(FaceRequest request) throws FaceException {
        FaceSearchResponse response = new FaceSearchResponse();

        if (!request.isValidReq()) {
            throw new FaceException(ErrorCodeMessage.INVALID_INPUT);
        }

        String filename = createFileName(request);
        request.setFilename(filename);

        FaceSearchRequest faceSearchRequest = new FaceSearchRequest();
        faceSearchRequest.setRequestId(request.getRequestId());
        faceSearchRequest.setUserId(request.getUserId());
        faceSearchRequest.setAlgReg("mobilenet");
        faceSearchRequest.setAlgDet("mobilenet");
        faceSearchRequest.setImageBase64(request.getImageBase64());
        faceSearchRequest.setType(request.getType());

        FaceImage faceImage = mapToFaceImageDto(request);

        try {
            faceImageRepository.save(faceImage);
            response = this.faceService.recognize(faceSearchRequest);

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(e.getMessage());
        }

        return response;
    }

    private FaceImage mapToFaceImageDto(FaceRequest request) {

        FaceImage faceImage = new FaceImage();
        faceImage.setFlow(request.getType());
        faceImage.setUserId(request.getUserId());
        faceImage.setRequestId(request.getRequestId());
        faceImage.setCreateDate(String.valueOf(System.currentTimeMillis()));
        faceImage.setUpdateDate(String.valueOf(System.currentTimeMillis()));
        faceImage.setFilename(request.getFilename());
        return faceImage;
    }

    private String createFileName(FaceRequest request) {
        return request.getType()+ "_" +
                request.getRequestId() + "_" + request.getUserId() + ".jpg";
    }


}
