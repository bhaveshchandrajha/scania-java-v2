# 🔴 URGENT: 500 Error Diagnosis for EC2 Deployment

## Current Error
```
HTTP 500 Internal Server Error
Path: /api/claims/search
```

## Likely Causes

### 1. Database Connection Issues (Most Likely)

The application is probably still using H2 database (file-based) and the database file is corrupted or has schema issues.

**Why:** The deploy.yml hasn't been updated yet with RDS profile, so it's still using H2.

### 2. Missing Database Tables

H2 schema might not have been created properly on startup.

### 3. Data.sql Seed Script Error

The seed script might be failing to execute.

---

## 🔍 How to Diagnose

### Step 1: Check Docker Container Logs

SSH into EC2 and check the logs:

```bash
# SSH to EC2
ssh ubuntu@3.27.167.200

# Check container logs
sudo docker logs warranty-container

# Look for errors like:
# - "Table not found"
# - "Connection refused"
# - "Schema validation failed"
# - "Error creating bean"
```

### Step 2: Check Which Database is Active

Look for these log entries:
```
# If using H2:
HikariPool-1 - Added connection conn0: url=jdbc:h2:file

# If using RDS:
HikariPool-1 - Added connection conn0: url=jdbc:postgresql
```

### Step 3: Verify RDS Profile

Check if RDS profile is active:
```bash
sudo docker inspect warranty-container | grep SPRING_PROFILES
# Should show: "SPRING_PROFILES_ACTIVE=rds"
```

---

## 🚑 Immediate Fixes

### Fix 1: Restart Container (Quick Try)

```bash
ssh ubuntu@3.27.167.200
sudo docker restart warranty-container
# Wait 30 seconds
curl http://localhost/api/claims/search?companyCode=001
```

### Fix 2: Rebuild Container with Fresh Database

```bash
ssh ubuntu@3.27.167.200

# Stop and remove container
sudo docker stop warranty-container
sudo docker rm warranty-container

# Remove old H2 database if it exists
sudo docker volume prune -f

# Run container (still using H2 until deploy.yml is updated)
sudo docker run -d \
  --name warranty-container \
  -p 80:8081 \
  --restart always \
  <dockerhub-username>/warranty-app:latest

# Check logs
sudo docker logs -f warranty-container
```

### Fix 3: Apply RDS Configuration (Recommended)

**This is the permanent solution:**

```bash
ssh ubuntu@3.27.167.200

# Stop and remove old container
sudo docker stop warranty-container
sudo docker rm warranty-container

# Pull latest image
sudo docker pull <dockerhub-username>/warranty-app:latest

# Run with RDS profile
sudo docker run -d \
  --name warranty-container \
  -p 80:8081 \
  -e SPRING_PROFILES_ACTIVE=rds \
  --restart always \
  <dockerhub-username>/warranty-app:latest

# Monitor startup
sudo docker logs -f warranty-container
```

**Look for successful startup:**
```
Started WarrantyApplication in X.XXX seconds
HikariPool-1 - Added connection conn0: url=jdbc:postgresql://database-1...
Tomcat started on port 8081
```

---

## 📊 Expected Log Patterns

### Successful Startup (H2)
```
HikariPool-1 - Start completed
H2 console available at '/h2-console'
Started WarrantyApplication in 2.x seconds
DataInitializer: Seeded X invoices
DataInitializer: Seed complete. Total claims in DB: X
Tomcat started on port 8081
```

### Successful Startup (RDS)
```
HikariPool-1 - Start completed
[no H2 console message]
Started WarrantyApplication in 2.x seconds
DataInitializer: Seeded X invoices
Tomcat started on port 8081
```

### Failed Startup
```
Error creating bean
Caused by: org.h2.jdbc.JdbcSQLException: Table not found
Failed to start bean 'dataSource'
Application run failed
```

---

## 🔧 Common Error Solutions

### Error: "Table CLAIM not found"

**Cause:** Schema not created  
**Fix:** Check `spring.jpa.hibernate.ddl-auto=update` in application.properties

**Manual Fix on EC2:**
```bash
# Access H2 console at: http://3.27.167.200/h2-console
# JDBC URL: jdbc:h2:file:./data/warranty_db
# User: sa
# Password: (empty)
# Run: SELECT * FROM CLAIM;
```

### Error: "Connection refused to database-1.cfmmi6uikb1h..."

**Cause:** RDS not accessible from EC2  
**Fix:** Update RDS security group

```bash
# Check EC2 security group ID
aws ec2 describe-instances --instance-ids <ec2-instance-id>

# Update RDS security group to allow EC2
aws rds modify-db-instance \
  --db-instance-identifier database-1 \
  --vpc-security-group-ids <sg-id-allowing-ec2>
```

### Error: "Access denied for user 'postgres'"

**Cause:** Wrong RDS credentials  
**Fix:** Verify credentials in application-rds.properties match RDS

---

## 🎯 Action Plan

### Immediate (Stop the 500 errors)

1. SSH to EC2: `ssh ubuntu@3.27.167.200`
2. Check logs: `sudo docker logs warranty-container | tail -100`
3. Share the error output for diagnosis
4. Try Fix #1 (restart) first

### Short-term (Use RDS)

1. Apply the deploy.yml changes manually (see RDS_CONFIGURATION_REQUIRED.md)
2. Or run Fix #3 above to manually deploy with RDS
3. Verify RDS security group allows EC2 connection

### Long-term (Automate)

1. Merge PR #12 with all fixes
2. Update deploy.yml in main branch
3. Future deployments will use RDS automatically

---

## 📞 Next Steps

**Right Now:**
1. SSH to EC2
2. Run: `sudo docker logs warranty-container | tail -100 > logs.txt`
3. Share the logs for specific diagnosis

**The logs will show exactly why the 500 error is happening.**

Common log locations to check:
- Last 100 lines: `sudo docker logs warranty-container | tail -100`
- Search for ERROR: `sudo docker logs warranty-container | grep ERROR`
- Search for exception: `sudo docker logs warranty-container | grep -i exception`

---

## 🆘 Quick Diagnostic Commands

```bash
# Check if container is running
sudo docker ps | grep warranty

# Check container environment
sudo docker inspect warranty-container | grep -A 10 Env

# Check which database is configured
sudo docker logs warranty-container | grep -i "hikari\|h2\|postgres"

# Check application startup
sudo docker logs warranty-container | grep "Started WarrantyApplication"

# Check for errors
sudo docker logs warranty-container | grep -E "ERROR|Exception|Failed"
```

**After running these, we can pinpoint the exact issue!**
