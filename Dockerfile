FROM eclipse-temurin:17-jre-alpine
COPY forum.jar /usr/src/
WORKDIR /usr/src/
CMD ["java", "-jar", "forum.jar", "-XX:+UseSerialGC", "-Xss512k"]
