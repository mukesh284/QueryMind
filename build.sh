#!/bin/bash

# QueryMind Build and Deploy Script
set -e

echo "================================"
echo "QueryMind Build Script"
echo "================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check Java version
echo -e "${YELLOW}Checking Java version...${NC}"
java -version

# Check Maven
echo -e "${YELLOW}Checking Maven...${NC}"
mvn -version

# Clean and build
echo -e "${YELLOW}Building application...${NC}"
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Build successful!${NC}"
else
    echo -e "${RED}Build failed!${NC}"
    exit 1
fi

# Run tests
echo -e "${YELLOW}Running tests...${NC}"
mvn test

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Tests passed!${NC}"
else
    echo -e "${RED}Tests failed!${NC}"
    exit 1
fi

# Build Docker image
echo -e "${YELLOW}Building Docker image...${NC}"
docker build -t querymind:1.0.0 .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Docker image built successfully!${NC}"
else
    echo -e "${RED}Docker build failed!${NC}"
    exit 1
fi

echo -e "${GREEN}================================"
echo "Build Complete!"
echo "================================${NC}"
echo ""
echo "Next steps:"
echo "1. Run locally: mvn spring-boot:run"
echo "2. Run Docker: docker run -p 8080:8080 querymind:1.0.0"
echo "3. Run Docker Compose: docker-compose up -d"
echo ""

