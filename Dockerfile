FROM openjdk:17-bullseye
WORKDIR /app
COPY target/*.jar /app/application.jar/
COPY gateway-routes.json /gateway-routes.json
CMD [ "java", "-jar", "/app/application.jar" ]