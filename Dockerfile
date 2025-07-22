

# === Build Stage ===
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn/ .mvn/
COPY src/ ./src/

RUN ./mvnw clean package -DskipTests

# === Runtime Stage ===
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy only the built JAR file
COPY --from=build /app/target/url-shortner-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional, helpful for docs)
EXPOSE 8085

# Run app using env variables passed at runtime by Render
CMD ["java", "-jar", "app.jar"]

