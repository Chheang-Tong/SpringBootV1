export APP_JWT_SECRET=$(openssl rand -base64 48)
PORT="${PORT:-8080}"
./mvnw clean spring-boot:run -Dspring-boot.run.arguments="--server.port=${PORT}"
