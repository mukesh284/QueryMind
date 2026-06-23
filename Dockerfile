# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy pom.xml first
COPY pom.xml .

# Download dependencies
RUN mvn -B -DskipTests \
    -Dmaven.wagon.http.retryHandler.class=standard \
    -Dmaven.wagon.http.retryHandler.count=3 \
    -Dmaven.wagon.reuseConnections=true \
    dependency:resolve 2>&1 | grep -v "^\[DEBUG\]" || true

# Copy source code
COPY src ./src

# Build the application - simple, clean approach
RUN mvn -B clean package \
    -DskipTests \
    -Dmaven.test.skip=true \
    2>&1 | tail -50
# Verify jar exists
RUN echo "=== TARGET ===" && ls -lah /build/target

RUN echo "=== JARS ===" && find /build -name "*.jar"
# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /build/target/querymind-ai-assistant-1.0.0.jar /app/querymind.jar
# Expose port
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -version || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "querymind.jar"]
