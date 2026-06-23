@echo off
REM QueryMind Setup Script for Windows - No Maven Required
REM This script sets up the project using Docker

echo ================================
echo QueryMind Setup ^& Build
echo ================================
echo.
echo Maven is not installed. Using Docker to build...
echo.

REM Check if Docker is installed
docker --version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Docker is not installed
    echo Please install Docker Desktop from https://www.docker.com/products/docker-desktop
    exit /b 1
)

echo Docker found. Building application...
echo.

REM Build the Docker image
docker build -t querymind:1.0.0 .

if errorlevel 1 (
    echo Build failed!
    exit /b 1
)

echo.
echo ================================
echo Build Complete!
echo ================================
echo.
echo To run the application:
echo   docker run -p 8080:8080 querymind:1.0.0
echo.
echo Or use Docker Compose:
echo   docker-compose up -d
echo.

