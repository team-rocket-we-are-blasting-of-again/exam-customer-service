FROM tobiaszimmer/exam-gateway-subscription:java-17
ARG jar_file

COPY $jar_file /app/application.jar
COPY gateway-routes.json /gateway-routes.json