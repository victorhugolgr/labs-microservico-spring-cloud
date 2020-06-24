FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]