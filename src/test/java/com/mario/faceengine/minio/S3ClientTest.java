package com.mario.faceengine.minio;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.helpers.HttpClient;
import com.mario.faceengine.model.FaceRegistrationRequest;
import com.mario.faceengine.model.FaceRegistrationResponse;
import com.mario.faceengine.model.Flow;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class S3ClientTest {

    private MinioClient minioClient;
    private AppConfig config;
    private S3Client s3Client;

    @BeforeEach
    public void setUp() {
        config = mock(AppConfig.class);
        minioClient = mock(MinioClient.class);
        s3Client = new S3Client(); // Initialize your service class here
        s3Client.setClient(minioClient); // Set the mocked client
    }

    @Test
    public void testS3Client_getClientSuccessWithBucketExist() throws Exception {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);

        MinioClient client = s3Client.getClient(config);

        assertNotNull(client);
        verify(minioClient, times(1)).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, never()).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    public void testS3Client_getClientSuccessWithNoBucketExistWithAutoCreate() throws Exception {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);

        // Assuming autoCreate is a field in your service class
        s3Client.setAutoCreate(true);

        MinioClient client = s3Client.getClient(config);

        assertNotNull(client);
        verify(minioClient, times(1)).makeBucket(any(MakeBucketArgs.class));
    }

    @Test
    public void testS3Client_getClientSuccessWithNoBucketExistWithNoAutoCreate() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);

        // Set autoCreate to false
        s3Client.setAutoCreate(false);

        Exception exception = assertThrows(FaceException.class, () -> {
            s3Client.getClient(config);
        });

        assertEquals(ErrorCodeMessage.S3_BUCKET_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testS3Client_getClientWrongCredentials() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        doThrow(new RuntimeException("Wrong Credential")).when(minioClient).bucketExists(any(BucketExistsArgs.class));

        Exception exception = assertThrows(FaceException.class, () -> {
            s3Client.getClient(config);
        });

        assertEquals(ErrorCodeMessage.S3_CLIENT_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testS3Client_uploadSuccess() throws Exception {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);

        String filePath = "path/to/file.jpg";
        String imageBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAACNbyblAAAAHElEQVR42mJ0UAAABZABe0b8IAAAAABJRU5ErkJggg==";
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        // Call the upload method
        s3Client.upload(filePath, imageBase64, config);

        verify(minioClient, times(1)).putObject(
                argThat(args -> {
                    try {
                        return "my-bucket".equals(args.bucket()) &&
                                filePath.equals(args.object()) &&
                                "image/jpeg".equals(args.contentType()) &&
                                args.stream() != null &&
                                args.objectSize() == imageBytes.length; // Validate the size as well
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    @Test
    public void testS3Client_uploadFailed() throws Exception {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
        String filePath = "path/to/file.jpg";
        String imageBase64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAACNbyblAAAAHElEQVR42mJ0UAAABZABe0b8IAAAAABJRU5ErkJggg==";

        doThrow(new RuntimeException("Upload failed")).when(minioClient).putObject(any(PutObjectArgs.class));

        FaceException exception = assertThrows(FaceException.class, () -> {
            s3Client.upload(filePath, imageBase64, config);
        });

        assertEquals(ErrorCodeMessage.S3_UPLOAD_ERROR.getCode(), exception.getErrorCode());
    }

    @Test
    public void testS3Client_uploadWrongType() throws Exception {

        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);

        String filePath = "path/to/file.jpg";
        String imageBase64 = "invalid_base64_string";

        FaceException faceException = assertThrows(FaceException.class, () -> {
            s3Client.upload(filePath, imageBase64, config);
        });

        assertEquals(ErrorCodeMessage.INVALID_BASE64.getCode(), faceException.getErrorCode());
    }

    @Test
    public void testS3Client_uploadFilePathNull() throws Exception {
        when(config.getS3Url()).thenReturn("http://localhost");
        when(config.getS3Port()).thenReturn(9000);
        when(config.getS3Username()).thenReturn("user");
        when(config.getS3Password()).thenReturn("password");
        when(config.getS3Bucket()).thenReturn("my-bucket");

        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);

        String imageBase64 = "";
        String filePath = "";

        FaceException faceException = assertThrows(FaceException.class, () -> {
            s3Client.upload(filePath, imageBase64, config);
        });

        assertEquals(ErrorCodeMessage.FILENAME_NOT_FOUND.getCode(), faceException.getErrorCode());
    }

}
