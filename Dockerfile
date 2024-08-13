FROM openjdk:17
ADD target/calendar-assessment-application-docker.jar calendar-assessment-application-docker.jar
ENTRYPOINT ["java", "-jar", "calendar-assessment-application-docker.jar"]
EXPOSE 8080
WORKDIR /app