FROM openjdk:21-ea-21-slim
ARG JAR_FILE=build/libs/kafka-playground-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} kafka-playground-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","/kafka-playground-0.0.1-SNAPSHOT.jar"]
