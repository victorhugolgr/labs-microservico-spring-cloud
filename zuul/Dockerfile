FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/zuul-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "zuul-0.0.1-SNAPSHOT.jar"]