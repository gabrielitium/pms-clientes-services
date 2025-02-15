
FROM gradle:8.6-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/

RUN gradle clean --no-daemon --stacktrace

COPY . /app

RUN gradle build --no-daemon --stacktrace

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/pms-clientes*.jar /app/pms-clientes-services.jar

# Expose port 8083 if your app listens on that port
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "/app/pms-clientes-services.jar"]

