# --- Stage 1: Build Stage ---
# Use Maven with JDK 17 to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy the warranty_demo pom.xml and fetch dependencies
COPY warranty_demo/pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copy the warranty_demo source code and resources (including static/angular/)
COPY warranty_demo/src ./src
RUN mvn clean package -Dmaven.test.skip=true

# --- Stage 2: Runtime Stage ---
# Use a lightweight JRE 17 image for the final container
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 3. Copy the built JAR from the build stage
# Based on your pom.xml: <artifactId>warranty-claim-management</artifactId> and <version>1.0.0</version>
COPY --from=build /app/target/warranty-claim-management-1.0.0.jar app.jar

# 4. Expose the Spring Boot port (8081 from application.properties)
EXPOSE 8081

# 5. Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
