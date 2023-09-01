FROM eclipse-temurin:17-jdk-alpine
#FROM gcr.io/distroless/java17-debian11
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#COPY target/authserver-*-SNAPSHOT.jar /app/auth-server.jar
#WORKDIR /app
#CMD ["auth-server.jar"]