# Stage 1: Build with Maven
FROM maven:3.8.1-openjdk-11 as builder

WORKDIR /build

# Copy pom.xml
COPY pom.xml .

# Download dependencies with retries and better settings
RUN mvn -B -DskipTests \
    -Dmaven.wagon.http.retryHandler.class=standard \
    -Dmaven.wagon.http.retryHandler.count=3 \
    -Dmaven.wagon.reuseConnections=true \
    dependency:resolve

# Copy source code
COPY src ./src

# Build the application with better memory settings
RUN mvn clean package -DskipTests \
    -Xmx1024m \
    -XX:+UseG1GC \
    -Dmaven.test.skip=true

# Stage 2: Runtime
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /build/target/querymind-ai-assistant-1.0.0.jar querymind.jar

# Expose port
EXPOSE 8080

# Set environment variables with better JVM settings
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC"

# Run the application
ENTRYPOINT ["java", "-jar", "querymind.jar"]
