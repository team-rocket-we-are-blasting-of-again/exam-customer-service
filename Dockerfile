FROM openjdk:17-bullseye
WORKDIR /app
COPY exam-customer-service/target/*.jar /app/application.jar

CMD [ "java", "-jar", "/app/application.jar" ]