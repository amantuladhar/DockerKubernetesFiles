FROM openjdk:8-jre-alpine
WORKDIR /myApp
EXPOSE 8080
RUN apk add --no-cache curl
COPY ["target/*.jar", "./app.jar"]
CMD ["java", "-jar", "app.jar"]
HEALTHCHECK  --start-period=10s --interval=15s --timeout=5s --retries=3 \
             CMD curl --fail http://localhost:8080/test/status-5xx || exit 1