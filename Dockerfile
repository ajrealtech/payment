FROM eclipse-temurin:17-jdk-alpine
VOLUME C:\dockerVol
ARG target\*.jar
COPY  target/*.jar payment.jar
ENTRYPOINT ["java","-jar","/payment.jar"]