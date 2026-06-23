# Stage 1: Build with Maven
FROM maven:3.8.1-openjdk-11 as builder

WORKDIR /build

# Copy pom.xml
COPY pom.xml .

# Download dependencies (this layer will be cached)
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /build/target/querymind-ai-assistant-1.0.0.jar querymind.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["java", "-jar", "querymind.jar"]
