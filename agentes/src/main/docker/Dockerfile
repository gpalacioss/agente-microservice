FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER gpalacios@legosoft.com.mx
ADD agentes-0.0.1-SNAPSHOT.jar agente-mocroservice.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
