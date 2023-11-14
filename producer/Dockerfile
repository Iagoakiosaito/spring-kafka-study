FROM openjdk:20-jdk-slim
LABEL author="Iago"

LABEL maintainer="Brainweb"
LABEL version="1.0"

EXPOSE 8080

COPY ./target/*.jar producer.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-jar", "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", "producer.jar"]