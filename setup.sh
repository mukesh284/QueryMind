#!/usr/bin/env bash

# QueryMind Setup Script - No Maven Required
# This script sets up the project using Docker

echo "================================"
echo "QueryMind Setup & Build"
echo "================================"
echo ""
echo "Maven is not installed. Using Docker to build..."
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "ERROR: Docker is not installed"
    echo "Please install Docker from https://www.docker.com/products/docker-desktop"
    exit 1
fi

echo "Docker found. Building application..."
echo ""

# Build the Docker image
docker build -t querymind:1.0.0 .

if [ $? -eq 0 ]; then
    echo ""
    echo "================================"
    echo "Build Complete!"
    echo "================================"
    echo ""
    echo "To run the application:"
    echo "  docker run -p 8080:8080 querymind:1.0.0"
    echo ""
    echo "Or use Docker Compose:"
    echo "  docker-compose up -d"
    echo ""
else
    echo "Build failed!"
    exit 1
fi

