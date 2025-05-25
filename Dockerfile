# Use a lightweight OpenJDK base image with Java 17 (common for Spring Boot)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file from the target directory (adjust the path if needed)
# Assumes the JAR is built and located in target/
COPY target/Secure-App-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080, change if customized)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]