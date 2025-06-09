FROM eclipse-temurin:21-jre-jammy

ARG JAR_FILE=build/libs/VirtualCollections-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java","-jar","/app.jar"]