
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests


FROM tomcat:10.1-jdk17
WORKDIR /usr/local/tomcat/webapps/


RUN rm -rf ROOT && rm -rf ROOT.war


COPY --from=build /app/target/fitforlife_pagina-1.0-SNAPSHOT.war ./ROOT.war


EXPOSE 8080
CMD ["catalina.sh", "run"]
