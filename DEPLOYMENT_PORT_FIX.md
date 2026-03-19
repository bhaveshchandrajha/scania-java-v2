# Deployment Port Fix Required

## Issue
The deploy.yml workflow needs to be updated to map the correct port.

## Current Configuration
```yaml
-p 80:8080
```

## Required Fix
```yaml
-p 80:8081
```

## Reason
The Spring Boot application runs on port 8081 (configured in `warranty_demo/src/main/resources/application.properties`), not 8080.

## Update Location
File: `.github/workflows/deploy.yml`
Line: ~52

Change this line:
```yaml
-p 80:8080 \
```

To:
```yaml
-p 80:8081 \
```

## Full Context
```yaml
sudo docker run -d \
  --name warranty-container \
  -p 80:8081 \
  --restart always \
  ${{ secrets.DOCKERHUB_USERNAME }}/warranty-app:latest
```

This maps EC2's port 80 (HTTP) to the container's port 8081 (Spring Boot).
