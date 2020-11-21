FROM openjdk:8-jdk-alpine
MAINTAINER Mateusz Harazin

COPY target/bagsoptimiser-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]