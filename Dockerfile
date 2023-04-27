FROM openjdk:17-jdk-slim
ADD ./build/libs/Clevertec-Spring-Rest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENV DB_USERNAME=user
ENV DB_PASSWORD=user
ENV DB_URL=jdbc:postgresql://db:5432/ecl
ENTRYPOINT ["java", "-jar", "app.jar"]