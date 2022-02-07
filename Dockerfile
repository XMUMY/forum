FROM eclipse-temurin:11-jre-alpine
COPY forum.jar /usr/src/
WORKDIR /usr/src/
CMD ["java", "-jar", "forum.jar"]
