FROM openjdk:8-jre-alpine
WORKDIR /docker-kubernetes
COPY ["/target/*.jar", "app.jar"]
CMD ["java", "-jar", "app.jar"]