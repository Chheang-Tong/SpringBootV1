
rm -f docker-compose.yml        
echo "APP_JWT_SECRET=change-me" > .env
echo "APP_JWT_EXP_MINUTES=60" >> .env

./mvnw clean package -DskipTests
ls -l target/                  
jar tf target/demo-0.0.1-SNAPSHOT.jar | grep boot/loader -m1 

docker compose down -v
docker compose up --build
