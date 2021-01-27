FROM openjdk:11
ARG JAR_FILE
COPY /server/target/Server-0.1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
