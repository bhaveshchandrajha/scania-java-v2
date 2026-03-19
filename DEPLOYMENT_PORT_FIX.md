# Deployment Configuration Updates

## Changes Made

### 1. Port Mapping Fix ✅
**Changed from:** `-p 80:8080`  
**Changed to:** `-p 80:8081`  
**Reason:** Spring Boot runs on port 8081 (configured in application.properties)

### 2. RDS PostgreSQL Configuration ✅
**Added:** `-e SPRING_PROFILES_ACTIVE=rds`  
**Reason:** Use AWS RDS PostgreSQL instead of ephemeral H2 database

## RDS Database Details

**Endpoint:** `database-1.cfmmi6uikb1h.ap-southeast-2.rds.amazonaws.com:5432`  
**Database:** `postgres`  
**Username:** `postgres`  
**Password:** Configured in `application-rds.properties`  
**Region:** ap-southeast-2 (Sydney)

## Benefits

1. **Data Persistence:** All claims and data persist across deployments
2. **Production Ready:** PostgreSQL is suitable for production workloads
3. **Correct Port:** Application accessible on port 80 (mapped to 8081)
4. **Automatic Schema:** Hibernate creates/updates schema automatically

## Final Configuration

```yaml
sudo docker run -d \
  --name warranty-container \
  -p 80:8081 \
  -e SPRING_PROFILES_ACTIVE=rds \
  --restart always \
  ${{ secrets.DOCKERHUB_USERNAME }}/warranty-app:latest
```

This configuration:
- Maps EC2 port 80 → container port 8081
- Activates RDS profile for PostgreSQL database
- Ensures data persistence across container restarts
