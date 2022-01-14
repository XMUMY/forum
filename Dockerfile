FROM openjdk:11
COPY forum.jar /usr/src/
WORKDIR /usr/src/
CMD ["java", "-jar", "forum.jar"]
