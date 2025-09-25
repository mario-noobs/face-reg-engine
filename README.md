# Face Recognition Engine

A Spring Boot-based microservice for face registration, recognition, and identity management using machine learning algorithms.

## Overview

The Face Recognition Engine provides REST APIs for:
- **Face Registration**: Register user faces with facial feature extraction
- **Face Recognition**: Identify users by comparing facial features
- **Identity Management**: Delete user identities and check registration status
- **Audit Logging**: Track all face operations with comprehensive logging
- **File Storage**: Store face images in MinIO object storage

## Technology Stack

- **Backend**: Spring Boot 3.3.4, Java 17
- **Database**: MySQL/MariaDB with JPA/Hibernate
- **Security**: JWT-based authentication
- **Storage**: MinIO for image storage
- **Build Tool**: Gradle
- **Containerization**: Docker & Docker Compose

## Features

### Core APIs
- `POST /face/v1/api/register-identity` - Register a new face identity
- `POST /face/v1/api/recognize-identity` - Recognize/search for a face
- `POST /face/v1/api/delete-identity` - Delete a user's face identity
- `GET /face/v1/api/is-registered?userId=xxx` - Check if user is registered

### Key Features
- Automatic face feature extraction and encoding
- Facial recognition with configurable algorithms (MobileNet)
- Comprehensive audit logging for all operations
- Secure JWT-based authentication
- Image storage with MinIO integration
- Database persistence with MySQL/MariaDB
- RESTful API design with proper error handling

## Prerequisites

- **Java 17** or higher
- **MySQL/MariaDB** database
- **MinIO** object storage (optional, for image storage)
- **Docker** and **Docker Compose** (for containerized deployment)

## Local Development Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd face-reg-engine
```

### 2. Database Setup
Create a MySQL/MariaDB database:
```sql
CREATE DATABASE FACE_ENGINE;
CREATE USER 'face_user'@'localhost' IDENTIFIED BY 'face_pass';
GRANT ALL PRIVILEGES ON FACE_ENGINE.* TO 'face_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Application Configuration
Update `src/main/resources/application.properties` for local development:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/FACE_ENGINE?useSSL=false
spring.datasource.username=face_user
spring.datasource.password=face_pass

# MinIO Configuration (optional)
minio.url=http://localhost
minio.port=9001
minio.username=admin
minio.password=123456789$
minio.bucket=face-bucket

# JWT Security
jwt.secret=your-jwt-secret-key-here
```

### 4. Run with Gradle
```bash
# Make gradlew executable
chmod +x gradlew

# Run the application
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 5. Build WAR file
```bash
./gradlew bootWar
```

## Docker Deployment

### Using Docker Compose
```bash
# Start the application with dependencies
docker-compose up -d
```

### Manual Docker Build
```bash
# Build the application
./gradlew bootWar

# Build Docker image
docker build -t face-engine:latest .

# Run container
docker run -p 8080:8080 face-engine:latest
```

## API Usage Examples

### 1. Register a Face
```bash
curl -X POST http://localhost:8080/face/v1/api/register-identity \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "requestId": "req-001",
    "imageBase64": "data:image/jpeg;base64,/9j/4AAQSkZJRgABA..."
  }'
```

### 2. Recognize a Face
```bash
curl -X POST http://localhost:8080/face/v1/api/recognize-identity \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "requestId": "req-002", 
    "imageBase64": "data:image/jpeg;base64,/9j/4AAQSkZJRgABA..."
  }'
```

### 3. Check Registration Status
```bash
curl -X GET "http://localhost:8080/face/v1/api/is-registered?userId=user123" \
  -H "Authorization: Bearer <your-jwt-token>"
```

### 4. Delete Identity
```bash
curl -X POST http://localhost:8080/face/v1/api/delete-identity \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "requestId": "req-003"
  }'
```

## Database Schema

The application uses the following main entities:
- **FaceFeature**: Stores facial encodings and user mappings
- **FaceImage**: Stores image metadata and file references
- **FaceAudit**: Audit trail for all face operations

## Configuration Options

### Environment Variables
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret
- `MINIO_URL`: MinIO server URL
- `MINIO_USERNAME`: MinIO access key
- `MINIO_PASSWORD`: MinIO secret key

### Algorithm Configuration
The service uses MobileNet algorithms for face detection and recognition. These can be configured in the service layer.

## Development

### Project Structure
```
src/main/java/com/mario/faceengine/
├── config/          # Application configuration
├── controller/      # REST API controllers
├── entity/          # JPA entities
├── exception/       # Custom exceptions
├── handler/         # Business logic handlers
├── model/          # Request/Response models
├── repository/     # JPA repositories
├── security/       # Security configuration
└── service/        # External service integrations
```

### Running Tests
```bash
./gradlew test
```

### Code Quality
The project includes:
- Spring Boot DevTools for hot reloading
- Comprehensive error handling
- Request/Response logging
- JWT-based security

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Verify MySQL/MariaDB is running
   - Check database credentials and URL
   - Ensure database exists

2. **MinIO Connection Issues**
   - Verify MinIO server is accessible
   - Check MinIO credentials
   - Ensure bucket exists or auto-create is enabled

3. **JWT Authentication**
   - Verify JWT secret is configured
   - Check token expiration
   - Ensure proper Authorization header format

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

[Add your license information here]

## Support

For issues and questions:
- Create an issue in the repository
- Contact the development team
- Check the application logs for detailed error information
