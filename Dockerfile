# Stage 1: Build the Java application (similar to the Go build in the second example)
FROM openjdk:17-jdk-alpine as builder

RUN mkdir /app
ADD . /app/
WORKDIR /app

# You can add a build step here if you have a build tool like Maven or Gradle.
# For example, for Gradle:
RUN ./gradlew bootJar

# Stage 2: Final image with a smaller Alpine base image
FROM alpine

RUN apk add --no-cache openjdk17

WORKDIR /app
COPY --from=builder /app/build/libs/face-engine-release.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "face-engine-release.jar"]
