FROM openjdk:11-jre
COPY forum.jar /usr/src/
WORKDIR /usr/src/
CMD ["java", "-jar", "forum.jar"]
