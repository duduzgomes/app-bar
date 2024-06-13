FROM eclipse-temurin:21-jdk-jammy
COPY . .
RUN ./mvnw clean install 
ENTRYPOINT [ "java", "-jar", "target\gomes-bar-0.0.1-SNAPSHOT.jar"]