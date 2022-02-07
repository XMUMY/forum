FROM eclipse-temurin:17-jre-alpine
COPY forum.jar /usr/src/
WORKDIR /usr/src/
RUN apk add gcompat
ENV LD_PRELOAD=/lib/libgcompat.so.0
CMD ["java", "-jar", "forum.jar"]
