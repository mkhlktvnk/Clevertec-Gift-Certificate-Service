FROM openjdk:17-jdk-slim
ADD ./build/libs/Clevertec-Spring-Rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]