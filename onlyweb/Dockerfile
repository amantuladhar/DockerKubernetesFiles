FROM openjdk:8-jre-alpine

WORKDIR myapp/

COPY ["target/*.jar", "app.jar"]

EXPOSE 8080

RUN apk add --no-cache curl

CMD ["java", "-jar", "app.jar"]