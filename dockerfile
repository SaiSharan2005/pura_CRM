# Stage 1: Build the application
FROM maven:3.8.6-openjdk-11 AS build
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and package it into a JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:11-jre-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/spring-boot-jwt-implementation-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port the app ruon
EXPOSE 8000

# Set environment variables for database and JWT configuration
# ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/pura_CRM
# ENV SPRING_DATASOURCE_USERNAME=myuser
# ENV SPRING_DATASOURCE_PASSWORD=mypassword
# ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
# ENV SECURITY_JWT_TOKEN_SECRET_KEY=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
# ENV SECURITY_JWT_TOKEN_EXPIRATION=2592000000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]