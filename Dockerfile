# Build stage: compile the Spring Boot application with Maven
FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /workspace

# Copy only the files required for building the jar
COPY pom.xml ./
COPY src ./src

# Build the application artifact without running tests
RUN mvn -B -DskipTests package

# Runtime stage: use a smaller JRE image to keep the final image compact
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copy the built application jar from the build stage
COPY --from=build /workspace/target/Quora-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
