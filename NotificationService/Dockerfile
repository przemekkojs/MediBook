FROM gradle:jdk21 as build

WORKDIR /app
COPY . .

RUN ["gradle","build"]

FROM openjdk:21-jdk

ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]