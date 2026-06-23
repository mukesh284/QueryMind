#!/bin/bash

# QueryMind Docker Compose Start Script
set -e

echo "================================"
echo "QueryMind Docker Compose Setup"
echo "================================"

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Check if .env file exists
if [ ! -f .env ]; then
    echo -e "${YELLOW}Creating .env file from .env.example...${NC}"
    cp .env.example .env
    echo -e "${YELLOW}Please edit .env file with your configuration${NC}"
    exit 1
fi

# Build and start containers
echo -e "${YELLOW}Building images...${NC}"
docker-compose build

echo -e "${YELLOW}Starting services...${NC}"
docker-compose up -d

# Wait for application to start
echo -e "${YELLOW}Waiting for application to start...${NC}"
sleep 10

# Check health
echo -e "${YELLOW}Checking application health...${NC}"
health=$(curl -s http://localhost:8080/querymind/api/v1/health || echo "failed")

if [[ $health == *"UP"* ]]; then
    echo -e "${GREEN}Application is running!${NC}"
    echo -e "${GREEN}API URL: http://localhost:8080/querymind${NC}"
    echo -e "${GREEN}H2 Console: http://localhost:8080/querymind/h2-console${NC}"
else
    echo -e "${YELLOW}Waiting a bit more for application to fully start...${NC}"
    sleep 10
fi

# Show status
echo ""
echo "Service Status:"
docker-compose ps

echo ""
echo "View logs: docker-compose logs -f querymind-app"
echo "Stop services: docker-compose down"

