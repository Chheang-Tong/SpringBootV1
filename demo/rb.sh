./mvnw clean package -DskipTests
jar tf target/*.jar | grep springframework/boot/loader | head
