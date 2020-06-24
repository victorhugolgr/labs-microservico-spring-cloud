FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/auth-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "auth-0.0.1-SNAPSHOT.jar"]