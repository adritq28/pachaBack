FROM openjdk:17-jdk-alpine
COPY target/proyecto-0.0.1-SNAPSHOT.jar pacha-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pacha-app.jar"]
