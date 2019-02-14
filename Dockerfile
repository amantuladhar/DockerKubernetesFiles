FROM openjdk:8-jre-alpine
WORKDIR /myApp
EXPOSE 8080
COPY ["target/*.jar", "./app.jar"]