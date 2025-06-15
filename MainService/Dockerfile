FROM alpine/openssl:3.5.0 as ssl
ARG KEYSTORE_PASSWORD
COPY /crt /ssl/crt

RUN openssl pkcs12 -export \
      -in /ssl/crt/domain.crt -inkey /ssl/crt/domain.key \
      -certfile /ssl/crt/rootCA.crt \
      -out client-keystore.p12 \
      -name client-alias \
      -passin pass:${KEYSTORE_PASSWORD} \
      -passout pass:${KEYSTORE_PASSWORD}

FROM openjdk:21-jdk as truststore
ARG KEYSTORE_PASSWORD
COPY /crt /ssl/crt

RUN keytool -import -alias root-ca \
    -file /ssl/crt/rootCA.crt -keystore client-truststore.p12 \
     -storetype PKCS12 -storepass ${KEYSTORE_PASSWORD} -noprompt

FROM gradle:jdk21 as build

WORKDIR /app
COPY . .
COPY --from=ssl /client-keystore.p12 ./src/main/resources/client-keystore.p12
COPY --from=truststore /client-truststore.p12 ./src/main/resources/client-truststore.p12

RUN ["gradle","build"]

FROM openjdk:21-jdk

ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar
COPY /crt /ssl/crt

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