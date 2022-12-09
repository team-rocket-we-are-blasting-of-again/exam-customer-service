FROM tobiaszimmer/exam-gateway-subscription:java-17
WORKDIR /app
COPY target/*.jar /application.jar/
COPY gateway-routes.json /gateway-routes.json