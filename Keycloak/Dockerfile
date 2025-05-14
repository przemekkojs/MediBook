FROM gradle:8.7-jdk17 AS client-build

WORKDIR /app

COPY ./ClientLoginListener .

RUN gradle clean shadowJar --no-daemon

FROM quay.io/keycloak/keycloak:24.0.4


COPY --from=client-build /app/build/libs/*.jar /opt/keycloak/providers/clientlistener.jar

# Entrypoint
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]