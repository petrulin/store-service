########
# Dockerfile to build store-service container image
#
########
FROM openjdk:17-slim

LABEL maintainer="Petrulin Alexander"

COPY target/store-service-*.jar app.jar

EXPOSE 8030

ENTRYPOINT ["java","-jar","/app.jar"]
