FROM openjdk:8-jdk-alpine
VOLUME /tmp
MAINTAINER gpalacios@legosoft.com.mx
#ARG JAR_FILE=target/agentes-0.0.1-SNAPSHOT.jar
#ADD target/agentes-0.0.1-SNAPSHOT.jar agente-service.jar
ARG JAR_FILE
COPY ${JAR_FILE} agente-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/agente-service.jar"]
