docker compose exec db pg_isready -U demo -d demo
docker compose exec db psql -U demo -d demo -c "select 1"
