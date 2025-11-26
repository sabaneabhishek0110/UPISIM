# Multi-stage Dockerfile for auth-service using Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /workspace

# copy pom and source
COPY pom.xml .
COPY src ./src

# Build the project (skip tests)
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jdk
WORKDIR /app

ARG JAR_FILE=/workspace/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar

ENV JAVA_OPTS="-Xms256m -Xmx768m -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]