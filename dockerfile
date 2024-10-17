FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/Microservices-Spring-Project-0.0.1-SNAPSHOT.jar /app/data-service-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/data-service-api.jar"]