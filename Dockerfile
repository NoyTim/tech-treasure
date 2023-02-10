FROM openjdk:8
EXPOSE 8080
ADD target/to-do-app.jar to-do-app.jar
ENTRYPOINT["java", "-jar", "/to-do-app.jar"]