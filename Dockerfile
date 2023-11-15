FROM amazoncorretto:17

ARG JAR_FILE=/build/libs/office-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /office.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/office.jar"]