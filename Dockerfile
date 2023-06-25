FROM eclipse-temurin:17-jre

RUN mkdir /app
WORKDIR /app

COPY build/libs/*-all.jar /app/bot.jar

CMD ["java", "-jar", "/app/bot.jar"]
