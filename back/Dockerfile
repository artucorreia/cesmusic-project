FROM maven AS build
LABEL authors="arthu"

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn clean install

FROM openjdk:21-jdk-slim

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "app.jar" ]