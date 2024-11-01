package com.mario.faceengine.service;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.helpers.HttpClient;
import com.mario.faceengine.logging.LogUtils;
import com.mario.faceengine.model.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class FaceServiceImpl implements FaceService {

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
}
