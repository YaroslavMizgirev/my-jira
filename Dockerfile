FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/my-jira-1.0.0.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

EXPOSE 22080

ENTRYPOINT ["java", "-jar", "app.jar"]