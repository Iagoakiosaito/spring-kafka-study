FROM openjdk:20-jdk-slim
LABEL author="Iago"

LABEL version="1.0"

EXPOSE 8080

COPY ./target/*.jar consumer.jar
#ADD target/interview-1.0.0-SNAPSHOT.jar interview.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-jar", "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", "consumer.jar"]