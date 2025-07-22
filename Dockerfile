# Use official OpenJDK base image
FROM eclipse-temurin:17-jdk-jammy

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
