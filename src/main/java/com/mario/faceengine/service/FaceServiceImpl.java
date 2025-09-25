package com.mario.faceengine.service;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.helpers.HttpClient;
import com.mario.faceengine.logging.LogUtils;
import com.mario.faceengine.model.*;
import com.mario.faceengine.repository.FaceFeatureRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceServiceImpl implements FaceService {

    @Autowired
    private FaceFeatureRepository faceFeatureRepository;

    @Override
    public FaceRegistrationResponse registerFace(FaceRegistrationRequest request) {

        FaceRegistrationResponse response = new FaceRegistrationResponse();

        try {
            AppConfig appConfig = AppConfig.getInstance();
            JSONObject params = request.toJson();
            String dataResponse = HttpClient.post(appConfig.getFaceHostNameUrl() +
                    appConfig.getRegisterPath(), params.toString());

            JSONObject jsonResponse = new JSONObject(dataResponse);
            response.setCode(jsonResponse.getString("code"));
            response.setMessage(jsonResponse.getString("message"));

            if (jsonResponse.has("data") && !jsonResponse.isNull("data")) {
                JSONObject data = jsonResponse.getJSONObject("data");
                if (data.has("user_id")) {
                    response.setUserId(data.getString("user_id"));
                }
                if (data.has("request_id")) {
                    response.setRequestId(data.getString("request_id"));
                }
                if (data.has("encoding_shape") && !data.isNull("encoding_shape")) {
                    // Convert JSONArray to int[]
                    org.json.JSONArray arr = data.getJSONArray("encoding_shape");
                    int[] shape = new int[arr.length()];
                    for (int i = 0; i < arr.length(); i++) {
                        shape[i] = arr.getInt(i);
                    }
                    response.setEncodingShape(shape);
                }
                if (data.has("face_encoding_base64") && !data.isNull("face_encoding_base64")) {
                    response.setFaceEncodingBase64(data.getString("face_encoding_base64"));
                }
            }

            // Save FaceFeature entity
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ErrorCodeMessage.NETWORK_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.NETWORK_ERROR.getMessage());
        }
        return response;
    }

    @Override
    public FaceSearchResponse recognize(FaceSearchRequest request) {
        LogUtils.logRequest("recognize", request.getUserId());
        FaceSearchResponse response = new FaceSearchResponse();
        response.setFlow(request.getType());
        response.setUserId(request.getUserId());
        response.setRequestId(request.getRequestId());
        try {
            AppConfig appConfig = AppConfig.getInstance();
            JSONObject params = request.toJson();
            String dataResponse = HttpClient.post(appConfig.getFaceHostNameUrl() +
                    appConfig.getRecognizePath(), params.toString());

            JSONObject jsonResponse = new JSONObject(dataResponse);

            response.setCode(jsonResponse.getString("code"));
            response.setMessage(jsonResponse.getString("message"));
            response.setSearchData(jsonResponse.getJSONObject("data"));

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ErrorCodeMessage.NETWORK_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.NETWORK_ERROR.getMessage());
        }
        LogUtils.logResponse("recognize", response.getSearchData().toString());
        return response;
    }

    @Override
    public DeleteIdentityResponse deleteFace(DeleteIdentityRequest request) {
        DeleteIdentityResponse response = new DeleteIdentityResponse();
        response.setRequestId(request.getRequestId());
        response.setUserId(request.getUserId());
        try {
            AppConfig appConfig = AppConfig.getInstance();
            JSONObject params = new JSONObject();
            params.put("userId", request.getUserId());
            params.put("algorithm", request.getAlgorithm());
            params.put("requestId", request.getRequestId());
            String url = appConfig.getFaceHostNameUrl() + appConfig.getDeletePath();
            String apiResponse = HttpClient.delete(url, params.toString());
            JSONObject json = new JSONObject(apiResponse);
            response.setMessage(json.optString("message"));
            response.setStatus(json.optString("status", "success"));
            response.setCode("success".equalsIgnoreCase(response.getStatus()) ? ErrorCodeMessage.SUCCESS.getCode() : "404");
        } catch (Exception e) {
            response.setCode(ErrorCodeMessage.UNKNOWN_ERROR.getCode());
            response.setMessage(ErrorCodeMessage.NETWORK_ERROR.getMessage());
            response.setStatus(ErrorCodeMessage.NETWORK_ERROR.toString());
        }
        return response;
    }
}
