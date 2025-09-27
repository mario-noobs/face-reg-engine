package com.mario.faceengine.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Testcontainers
@TestConfiguration
public class TestContainersConfig {
    private static final Logger log = LoggerFactory.getLogger(TestContainersConfig.class);

    static {
        System.out.println("[DEBUG] TestContainersConfig static block loaded");
        log.info("TestContainersConfig class loaded");
    }

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("FACE_ENGINE")
            .withUsername("root")
            .withPassword("root_password_secret_tcp")
            .withReuse(false);

    @Container
    public static MinIOContainer minio = new MinIOContainer("minio/minio:latest")
            .withUserName("admin")
            .withPassword("123456789$")
            .withReuse(false);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        System.out.println("[DEBUG] registerProperties called");
        log.info("Registering dynamic properties...");

        // MySQL
        registry.add("spring.datasource.url", () -> {
            String url = mysql.getJdbcUrl();
            System.out.println("[DEBUG] Injecting MySQL JDBC URL: " + url);
            log.info("Injecting MySQL JDBC URL: {}", url);
            return url;
        });
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        // MinIO
        registry.add("minio.endpoint", () -> {
            String endpoint = "http://localhost:" + minio.getFirstMappedPort();
            System.out.println("[DEBUG] Injecting MinIO endpoint: " + endpoint);
            log.info("Injecting MinIO endpoint: {}", endpoint);
            return endpoint;
        });
        registry.add("minio.port", () -> minio.getFirstMappedPort());
        registry.add("minio.username", () -> "admin");
        registry.add("minio.password", () -> "123456789$");

        System.out.println("[DEBUG] All properties registered");
    }
}
