FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/movieapp-0.0.1-SNAPSHOT.jar /app/movieapp.jar

ENTRYPOINT ["java", "-jar", "/app/movieapp.jar"]