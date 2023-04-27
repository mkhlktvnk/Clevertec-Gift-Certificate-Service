FROM openjdk:17-jdk-slim
LABEL maintainer="Kutovenko M.I."
WORKDIR /app
COPY ./build/libs/*.jar /app/
RUN apk update && apk add --no-cache bash
EXPOSE 8080
ENV DB_USERNAME=user
ENV DB_PASSWORD=user
ENV DB_URL=jdbc:postgresql://db:5432/ecl
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["$(ls /app/*.jar | grep -v original | head -n 1)"]