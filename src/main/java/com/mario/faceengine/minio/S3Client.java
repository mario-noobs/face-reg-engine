package com.mario.faceengine.minio;

import com.mario.faceengine.config.AppConfig;
import com.mario.faceengine.exception.ErrorCodeMessage;
import com.mario.faceengine.exception.FaceException;
import com.mario.faceengine.logging.LogUtils;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import io.minio.MinioClient;

/**
 * @author Nott
 * @Date 2023/8/8
 */

@Component
public class S3Client {

    private final Logger log = LoggerFactory.getLogger(S3Client.class);

    private MinioClient client;

    @Resource
    private MinioConfig minioProp;

    @Value("${minio.autocreate}")
    public boolean autoCreate;

    public MinioClient getClient(AppConfig config) throws Exception {
        if (this.client == null) {
            this.client = MinioClient.builder()
                            .endpoint(config.getS3Url(), config.getS3Port(), false)
                            .credentials(config.getS3Username(), config.getS3Password())
                            .build();
        }
        boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(config.getS3Bucket()).build());
        if (!found) {
            if (autoCreate) {
                client.makeBucket(MakeBucketArgs.builder().bucket(config.getS3Bucket()).build());
                log.info("Auto make bucket: {}", config.getS3Bucket());
            } else {
                throw new FaceException(ErrorCodeMessage.S3_BUCKET_ERROR);
            }
        }

        return this.client;
    }

    public void upload(String filePath, String imageBase64, AppConfig config) throws Exception {
        LogUtils.logRequest("upload", filePath);
        if (filePath == null || filePath.isEmpty()) {
            throw new FaceException(ErrorCodeMessage.FILENAME_NOT_FOUND);
        }

        MinioClient minioClient = getClient(config);

        // Decode the Base64 string
//        String base64Data = imageBase64.split(",")[1]; // Split the prefix (data:image/jpeg;base64,)
        byte[] imageBytes = Base64.getDecoder().decode(imageBase64);

        // Create an InputStream from the byte array
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        minioClient.putObject(
                PutObjectArgs.builder().bucket(config.getS3Bucket()).object(filePath)
                        .contentType("image/jpeg")
                        .stream(
                                inputStream, inputStream.available(), -1)
                        .build());
        log.info("File {},Upload Bucket {}", filePath, config.getS3Bucket());
        LogUtils.logResponse("upload", "");
    }

//    private MultipartFile handlePicCompress(MultipartFile file) throws Exception {
//        if (file.getSize() > maxSize) {
//            String surffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//            String fileName = StringUtils.isNotEmpty(file.getName()) ? file.getName() : UUID.randomUUID().toString().replaceAll("-", "");
//            String path = System.getProperty("java.io.tmpdir") + File.separator;
//            // 在项目根目录下的 upload 目录中生成临时文件
//            File newFile = new File(path + UUID.randomUUID().toString() + surffix);
//            // 小于 1M 的
//            if ((1024 * 1024 * 0.1) <= file.getSize() && file.getSize() <= (1024 * 1024)) {
//                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.3f).toFile(newFile);
//            }
//            // 1 - 2M 的
//            else if ((1024 * 1024) < file.getSize() && file.getSize() <= (1024 * 1024 * 2)) {
//                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.2f).toFile(newFile);
//            }
//            // 2M 以上的
//            else if ((1024 * 1024 * 2) < file.getSize()) {
//                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.1f).toFile(newFile);
//            }
//            // 获取输入流
//            FileInputStream input = new FileInputStream(newFile);
//            // 转为 MultipartFile
//            return new MockMultipartFile(fileName, file.getOriginalFilename(), file.getContentType(), input);
//        } else {
//            return file;
//        }
//    }

//    private boolean isPicture(MultipartFile file) {
//        String contentType = file.getContentType();
//        if (contentType.contains("image/")) {
//            return true;
//        }
//        return false;
//    }

//    public String getPreviewUrl(String fileName) throws Exception {
//        MinioClient client = this.getClient();
//        String url =
//                client.getPresignedObjectUrl(
//                        GetPresignedObjectUrlArgs.builder()
//                                .method(Method.GET)
//                                .bucket(minioProp.getBucket())
//                                .object(fileName)
//                                .expiry(1, TimeUnit.DAYS)
//                                .build());
//
//        return url;
//    }

//    public void removeObject(String filePath) throws Exception {
//        MinioClient client = this.getClient();
//        client.removeObject(
//                RemoveObjectArgs.builder().bucket(minioProp.getBucket()).object(filePath).build());
//    }

//    public void download(String filePath, String fileName, HttpServletResponse response) throws Exception {
//        MinioClient client = this.getClient();
//        // 创建输入流
//        InputStream is = null;
//        try {
//            // 获取对象的元数据
//            StatObjectResponse stat = client.statObject(StatObjectArgs.builder().bucket(minioProp.getBucket()).object(filePath).build());
//            // 响应 设置内容类型
//            response.setContentType(stat.contentType());
//            // 响应 设置编码格式
//            response.setCharacterEncoding("UTF-8");
//            // 响应 设置头文件
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            // 输入流
//            is = client.getObject(GetObjectArgs.builder().bucket(minioProp.getBucket()).object(filePath).build());
//            // 将字节从输入流复制到输出流
//            IOUtils.copy(is, response.getOutputStream());
//        } catch (Exception e) {
//            throw new RuntimeException("下载文件异常", e);
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
