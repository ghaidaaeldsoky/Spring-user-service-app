#FROM eclipse-temurin:17-jdk-jammy
#WORKDIR /app
#COPY target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Build stage
FROM maven:3.9.7-eclipse-temurin-17 AS build
WORKDIR /workspace/app
COPY pom.xml .
RUN mvn dependency:go-offline -B
#RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN adduser -D appuser
USER appuser
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]