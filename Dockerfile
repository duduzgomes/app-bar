FROM eclipse-temurin:21-jdk-jammy
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean install 
ENTRYPOINT [ "java", "-jar", "target\gomes-bar-0.0.1-SNAPSHOT.jar"]