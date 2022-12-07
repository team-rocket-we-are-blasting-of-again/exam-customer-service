FROM openjdk:17-bullseye
WORKDIR /app
COPY target/*.jar /app/application.jar/

CMD [ "java", "-jar", "/app/application.jar" ]

# USE THE BELOW IMPLEMENTATION WHEN READY FOR DEPLOYMENT
#FROM tobiaszimmer/exam-gateway-subscription:java-17
#WORKDIR /app
#COPY target/*.jar /application.jar/
#COPY gateway-routes.json /gateway-routes.json