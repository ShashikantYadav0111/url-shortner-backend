# Use official OpenJDK base image
FROM eclipse-temurin:17-jdk-jammy

# -------- Stage 1: Build --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy build files
COPY mvnw pom.xml ./
COPY .mvn/ .mvn/
COPY src/ ./src/

# Build the application
RUN mvn clean package

# Run the application
CMD ["java", "-jar", "target/url-shortner-0.0.1-SNAPSHOT.jar"]
