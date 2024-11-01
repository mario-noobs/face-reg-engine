package com.mario.faceengine.helpers;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpClient {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static OkHttpClient createHttpClient(int connectionTimeout, int readTimeout, int writeTimeout) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
//                .addInterceptor(logging)
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static String post(String url, String jsonBody) throws IOException {
        OkHttpClient client = createHttpClient(10, 30, 15);

        // Build the request body
        RequestBody body = RequestBody.create(jsonBody, JSON);

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string(); // Return response body as a string
        }
    }

}