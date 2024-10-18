FROM openjdk:17-jdk-alpine

RUN mkdir /app
ADD build/libs/. /app/
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "face-engine-0.5-release.jar"]