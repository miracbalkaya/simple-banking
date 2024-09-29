FROM openjdk:17-jdk-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} simple-banking.jar

ENTRYPOINT ["java", "-jar", "/simple-banking.jar"]

