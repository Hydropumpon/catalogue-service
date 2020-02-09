FROM openjdk:8-jre-alpine
COPY ./build/libs/catalogue-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "catalogue-service-0.0.1-SNAPSHOT.jar"]
