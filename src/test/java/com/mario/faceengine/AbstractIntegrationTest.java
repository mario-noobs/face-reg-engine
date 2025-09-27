package com.mario.faceengine;

import com.mario.faceengine.config.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
@Import(TestSecurityConfig.class)
public abstract class AbstractIntegrationTest {

    static {
        System.out.println("[DEBUG] AbstractIntegrationTest static block loaded");
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
        System.out.println("[DEBUG] registerProperties called in AbstractIntegrationTest");

        // MySQL
        registry.add("spring.datasource.url", () -> {
            String url = mysql.getJdbcUrl();
            System.out.println("[DEBUG] Injecting MySQL JDBC URL: " + url);
            return url;
        });
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);

        // MinIO
        registry.add("minio.endpoint", () -> {
            String endpoint = "http://localhost:" + minio.getFirstMappedPort();
            System.out.println("[DEBUG] Injecting MinIO endpoint: " + endpoint);
            return endpoint;
        });
        registry.add("minio.port", () -> minio.getFirstMappedPort());
        registry.add("minio.username", () -> "admin");
        registry.add("minio.password", () -> "123456789$");

        System.out.println("[DEBUG] All properties registered in AbstractIntegrationTest");
    }
}
