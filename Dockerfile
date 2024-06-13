FROM eclipse-temurin:21-jdk-jammy
COPY . .
RUN chmod +x mvn
RUN mvn clean install -DskipTests
ENTRYPOINT [ "java", "-jar", "target\gomes-bar-0.0.1-SNAPSHOT.jar"]