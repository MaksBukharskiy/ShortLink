FROM openjdk:17-ea-17-jdk-slim
WORKDIR /app
COPY /build/libs/ShortLink-0.0.1-SNAPSHOT.jar /app/shortlink.jar
ENTRYPOINT ["java", "-jar", "shortlink.jar"]