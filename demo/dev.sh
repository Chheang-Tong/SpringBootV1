export APP_JWT_SECRET=$(openssl rand -base64 48)
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=dev \
    -Dspring-boot.run.arguments="--server.port=8081"

