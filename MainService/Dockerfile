FROM gradle:jdk21 as build

WORKDIR /app
COPY . .

RUN ["gradle","build"]

FROM openjdk:21-jdk

ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar
COPY crt /ssl/crt

RUN keytool -import -trustcacerts -alias domain \
    -file /ssl/crt/domain.crt \
    -keystore "$JAVA_HOME/lib/security/cacerts" \
    -storepass changeit -noprompt

RUN keytool -import -trustcacerts -alias rootCA \
    -file /ssl/crt/rootCA.crt \
    -keystore "$JAVA_HOME/lib/security/cacerts" \
    -storepass changeit -noprompt

EXPOSE ${APP_PORT}

ENTRYPOINT ["java","-jar","app.jar"]