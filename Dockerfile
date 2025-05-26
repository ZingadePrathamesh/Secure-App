# ------------ Build Stage ------------
FROM maven:3.9.6-eclipse-temurin-17 as build

# Set the working directory inside the builder container
WORKDIR /build

# Copy all project files (adjust .dockerignore to exclude target if needed)
COPY . .

# Package the Spring Boot app, skipping tests for faster build (optional)
RUN mvn clean package -DskipTests

# ------------ Run Stage ------------
FROM openjdk:17-jdk-slim

# Set the working directory inside the final container
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /build/target/Secure-App-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
