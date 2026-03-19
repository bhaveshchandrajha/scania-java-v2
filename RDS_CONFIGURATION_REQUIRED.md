# ⚠️ Manual Configuration Required: Enable RDS PostgreSQL

## Overview
The deploy.yml workflow has been updated to use RDS PostgreSQL, but it cannot be pushed due to GitHub token scope limitations. You need to apply this change manually.

## Required Change

**File:** `.github/workflows/deploy.yml`  
**Lines:** 48-54

### Current Configuration (Wrong)
```yaml
# Run the new container 
# (Mapping EC2 Port 80 to Spring Boot Port 8080)
sudo docker run -d \
  --name warranty-container \
  -p 80:8080 \
  --restart always \
  ${{ secrets.DOCKERHUB_USERNAME }}/warranty-app:latest
```

### Updated Configuration (Correct)
```yaml
# Run the new container with RDS PostgreSQL
# (Mapping EC2 Port 80 to Spring Boot Port 8081)
sudo docker run -d \
  --name warranty-container \
  -p 80:8081 \
  -e SPRING_PROFILES_ACTIVE=rds \
  --restart always \
  ${{ secrets.DOCKERHUB_USERNAME }}/warranty-app:latest
```

## What Changed

1. **Port Mapping:** `80:8080` → `80:8081` (matches Spring Boot config)
2. **RDS Profile:** Added `-e SPRING_PROFILES_ACTIVE=rds` environment variable
3. **Comment:** Updated to reflect RDS usage

## How to Apply

### Option 1: Edit on GitHub (Recommended)

1. Go to: https://github.com/bhaveshchandrajha/scania-java-v2/blob/main/.github/workflows/deploy.yml
2. Click the **Edit** button (pencil icon)
3. Find lines 48-54
4. Replace the docker run command with the updated version above
5. Commit directly to `main` branch
6. The next push will trigger deployment with RDS

### Option 2: Edit Locally

If you have the repository cloned locally:

```bash
# Edit .github/workflows/deploy.yml
# Make the changes shown above
git add .github/workflows/deploy.yml
git commit -m "Enable RDS PostgreSQL and fix port mapping"
git push origin main
```

### Option 3: Apply to PR Branch First

Edit on the `warranty-demo-merge` branch, then merge the PR:

1. Go to: https://github.com/bhaveshchandrajha/scania-java-v2/blob/warranty-demo-merge/.github/workflows/deploy.yml
2. Make the changes
3. Commit to `warranty-demo-merge`
4. Merge PR #12
5. Changes will be in main

## RDS Database Configuration

The application will connect to:

- **Host:** `database-1.cfmmi6uikb1h.ap-southeast-2.rds.amazonaws.com`
- **Port:** `5432`
- **Database:** `postgres`
- **Username:** `postgres`
- **Password:** Configured in `application-rds.properties`

Configuration is in: `warranty_demo/src/main/resources/application-rds.properties`

## Benefits After Applying

✅ **Data Persistence:** Claims and data survive container restarts  
✅ **Production Database:** PostgreSQL instead of H2  
✅ **Correct Port:** Application accessible on port 80  
✅ **Automatic Schema:** Hibernate manages database schema  

## Verification

After applying and deploying:

1. Access: `http://3.27.167.200/angular/`
2. Create a claim
3. Restart the container: `sudo docker restart warranty-container`
4. The claim should still exist (data persisted in RDS)

## Troubleshooting

If the application fails to start after this change:

1. Check RDS is accessible from EC2
2. Verify security group allows EC2 → RDS connection
3. Check logs: `sudo docker logs warranty-container`
4. Verify RDS credentials are correct

## Current Status

- ✅ Dockerfile updated and pushed
- ✅ Angular UI files added and pushed
- ⚠️ deploy.yml changes ready (need manual apply)
- ✅ Documentation updated

**Action Required:** Apply the deploy.yml changes manually using one of the options above.
