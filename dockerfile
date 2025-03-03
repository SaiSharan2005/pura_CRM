# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and package it into a JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/spring-boot-jwt-implementation-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port the app runs on
EXPOSE 8000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
