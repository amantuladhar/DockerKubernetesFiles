FROM openjdk:8-jre-alpine
WORKDIR /myApp
COPY ["target/*.jar", "./app.jar"]