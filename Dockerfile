FROM eclipse-temurin:17-jdk-alpine
VOLUME C:\dockerVol
ARG target\*.jar
COPY  target/*.jar product.jar
ENTRYPOINT ["java","-jar","/product.jar"]