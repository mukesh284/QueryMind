FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the built jar file
COPY target/querymind-ai-assistant-1.0.0.jar querymind.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV AI_API_KEY=${AI_API_KEY}
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["java", "-jar", "querymind.jar"]

