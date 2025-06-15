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
RUN chmod 644 client-keystore.p12

FROM openjdk:21-jdk as truststore
ARG KEYSTORE_PASSWORD
COPY /crt /ssl/crt

RUN keytool -import -alias root-ca \
    -file /ssl/crt/rootCA.crt -keystore client-truststore.p12 \
     -storetype PKCS12 -storepass ${KEYSTORE_PASSWORD} -noprompt
RUN chmod 644 client-truststore.p12

FROM gradle:8.7-jdk17 AS client-build

WORKDIR /app

COPY ./ClientLoginListener .

RUN gradle clean shadowJar --no-daemon

FROM quay.io/keycloak/keycloak:24.0.4


COPY --from=client-build /app/build/libs/*.jar /opt/keycloak/providers/clientlistener.jar
COPY --from=ssl /client-keystore.p12 /opt/keycloak/client-keystore.p12
COPY --from=truststore /client-truststore.p12 /opt/keycloak/client-truststore.p12



# Entrypoint
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]