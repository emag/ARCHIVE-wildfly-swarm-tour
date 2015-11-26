FROM jboss/base-jdk:8
MAINTAINER emag

ADD target/lifelog-docker-swarm.jar /opt/lifelog-docker-swarm.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/lifelog-docker-swarm.jar"]
