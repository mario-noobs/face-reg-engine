package com.mario.faceengine.handler;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.entity.FaceAudit;
import com.mario.faceengine.entity.FaceImage;
import com.mario.faceengine.entity.FaceFeature;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.minio.S3Client;
import com.mario.faceengine.model.*;
import com.mario.faceengine.repository.FaceAuditRepository;
import com.mario.faceengine.repository.FaceFeatureRepository;
import com.mario.faceengine.repository.FaceImageRepository;
import com.mario.faceengine.service.FaceService;
import com.mario.faceengine.service.FaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
public class FaceHandler {

    @Autowired
    FaceService faceService;

    @Autowired
    FaceImageRepository faceImageRepository;

    @Autowired
    FaceAuditRepository faceAuditRepository;

    @Autowired
    FaceFeatureRepository faceFeatureRepository;

    public FaceRegistrationResponse registerIdentity(FaceRequest request) throws FaceException {
        FaceRegistrationResponse response = new FaceRegistrationResponse();

        if (!request.isValidReq()) {
            throw new FaceException(ErrorCodeMessage.INVALID_INPUT);
        }

        String filename = createFileName(request);
        request.setFilename(filename);

        FaceRegistrationRequest faceRegistrationRequest = new FaceRegistrationRequest();
        faceRegistrationRequest.setRequestId(request.getRequestId());
        faceRegistrationRequest.setUserId(request.getUserId());
        faceRegistrationRequest.setAlgReg("mobilenet");
        faceRegistrationRequest.setAlgDet("mobilenet");
        faceRegistrationRequest.setImageBase64(request.getImageBase64());
        faceRegistrationRequest.setType(request.getType());

        FaceImage faceImage = mapToFaceImageDto(request);

        try {
            faceImageRepository.save(faceImage);
            response = this.faceService.registerFace(faceRegistrationRequest);

            response.setRequestId(request.getRequestId());
            response.setUserId(request.getUserId());
            response.setType(request.getType());
            response.setCreateDate(String.valueOf(System.currentTimeMillis()));

            // Save or update FaceFeature entity here
            if (response.getFaceEncodingBase64() != null) {
                FaceFeature feature = faceFeatureRepository.findTopByUserIdOrderByCreateDateDesc(response.getUserId());
                if (feature == null) {
                    feature = new FaceFeature();
                    feature.setUserId(response.getUserId());
                    feature.setCreateDate(response.getCreateDate());
                } else {
                    feature.setUpdateDate(String.valueOf(System.currentTimeMillis()));
                }
                feature.setFeature(response.getFaceEncodingBase64());
                feature.setFlow(response.getType());
                feature.setActivate(1); // default to active
                faceFeatureRepository.save(feature);
            }

            response.setFaceEncodingBase64(null);

            S3Client s3Client = new S3Client();
            AppConfig appConfig = AppConfig.getInstance();
            s3Client.upload(filename, faceRegistrationRequest.getImageBase64(), appConfig);

            FaceAudit faceAudit = mapToFaceAudit(response);
            faceAuditRepository.save(faceAudit);

//            response.setCode(ErrorCodeMessage.SUCCESS.getCode());
//            response.setMessage(ErrorCodeMessage.SUCCESS.getMessage());

        } catch (FaceException e) {
            response.setCode(e.getErrorCode());
            response.setMessage(e.getErrorMessage());
        }
        catch (Exception e) {
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

            S3Client s3Client = new S3Client();
            AppConfig appConfig = AppConfig.getInstance();
            s3Client.upload(filename, faceSearchRequest.getImageBase64(), appConfig);

            FaceAudit faceAudit = mapToFaceAudit(response);
            faceAuditRepository.save(faceAudit);

        } catch (FaceException e) {
            response.setCode(e.getErrorCode());
            response.setMessage(e.getErrorMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(e.getMessage());
        }

        return response;
    }

    public DeleteIdentityResponse deleteIdentity(FaceRequest request) {

        DeleteIdentityRequest deleteIdentityRequest = new DeleteIdentityRequest();
        deleteIdentityRequest.setRequestId(request.getRequestId());
        deleteIdentityRequest.setUserId(request.getUserId());
        deleteIdentityRequest.setAlgorithm("mobilenet");

        DeleteIdentityResponse response = faceService.deleteFace(deleteIdentityRequest);
        try {
            // Find latest FaceFeature for userId
            FaceFeature feature = faceFeatureRepository.findTopByUserIdOrderByCreateDateDesc(request.getUserId());
            if (feature != null && "success".equalsIgnoreCase(response.getStatus())) {
                feature.setActivate(0);
                faceFeatureRepository.save(feature);
            }
            // Save FaceAudit with flow DELETE
            FaceAudit faceAudit = new FaceAudit();
            faceAudit.setFlow(request.getType());
            faceAudit.setUserId(request.getUserId());
            faceAudit.setRequestId(request.getRequestId());
            faceAudit.setCreateDate(String.valueOf(System.currentTimeMillis()));
            faceAudit.setUpdateDate(String.valueOf(System.currentTimeMillis()));
            faceAudit.setCode(response.getCode());
            faceAudit.setMessage(response.getMessage());
            faceAuditRepository.save(faceAudit);
        } catch (Exception e) {
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage("Delete identity failed: " + e.getMessage());
            response.setStatus("error");
        }
        return response;
    }

    public boolean isUserRegistered(String userId) {
        return faceFeatureRepository.existsByUserIdAndActivate(userId, 1);
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

    private FaceAudit mapToFaceAudit(FaceSearchResponse response) {

        FaceAudit faceAudit = new FaceAudit();
        faceAudit.setFlow(response.getFlow());
        faceAudit.setUserId(response.getUserId());
        faceAudit.setRequestId(response.getRequestId());
        faceAudit.setCreateDate(String.valueOf(System.currentTimeMillis()));
        faceAudit.setUpdateDate(String.valueOf(System.currentTimeMillis()));
        faceAudit.setCode(response.getCode());
        faceAudit.setMessage(response.getMessage());

        if (Objects.equals(response.getFlow(), Flow.RECOGNIZE.getFlow()) && response.getSearchData() != null) {
            faceAudit.setFaceSearch(response.getSearchData().toString());
        }

        return faceAudit;
    }

    private FaceAudit mapToFaceAudit(FaceRegistrationResponse response) {

        FaceAudit faceAudit = new FaceAudit();
        faceAudit.setFlow(response.getType());
        faceAudit.setUserId(response.getUserId());
        faceAudit.setRequestId(response.getRequestId());
        faceAudit.setCreateDate(String.valueOf(System.currentTimeMillis()));
        faceAudit.setUpdateDate(String.valueOf(System.currentTimeMillis()));
        faceAudit.setCode(response.getCode());
        faceAudit.setMessage(response.getMessage());

        return faceAudit;
    }

    private String createFileName(FaceRequest request) {
        return request.getType()+ "_" +
                request.getRequestId() + "_" + request.getUserId() + ".jpg";
    }

}
