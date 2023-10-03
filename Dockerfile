FROM amazoncorretto:17.0.8-alpine
LABEL authors="melihhilmiuludag"
MAINTAINER www.melihhilmiuludag.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} readingisgood.jar
ENTRYPOINT ["java","-jar","/readingisgood"]