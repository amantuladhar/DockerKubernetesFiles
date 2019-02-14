FROM openjdk:8-jre-alpine
WORKDIR /myApp
EXPOSE 8080
RUN apk add --no-cache curl
COPY ["target/*.jar", "./app.jar"]
CMD ["java", "-jar", "app.jar"]