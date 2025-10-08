# Stop and remove containers (keeps DB volume)
docker compose down

# Stop & wipe volumes (resets DB)
docker compose down -v

# Rebuild after code changes
docker compose up --build -d

# Exec into the DB container
docker compose exec db psql -U demo -d demo
