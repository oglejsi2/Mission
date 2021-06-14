FROM java:8-jdk-alpine
COPY /target/mission-0.0.1-SNAPSHOT.war /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mission-0.0.1-SNAPSHOT.war"]