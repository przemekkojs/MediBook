#!/bin/bash


set -e

# Define filenames
CA_KEY="rootCA.key"
CA_CERT="rootCA.crt"
CA_SERIAL="rootCA.srl"
SERVER_KEY="domain.key"
SERVER_CSR="domain.csr"
SERVER_CERT="domain.crt"
CONFIG_FILE="openssl-san.cnf"

echo "### Generating CA private key"
openssl genrsa -out ${CA_KEY} 4096

echo "### Generating CA certificate"
openssl req -x509 -new -nodes -key ${CA_KEY} -sha256 -days 1024 \
  -out ${CA_CERT} -config ${CONFIG_FILE}

echo "### Generating server private key"
openssl genrsa -out ${SERVER_KEY} 2048

echo "### Generating server certificate signing request (CSR) using ${CONFIG_FILE}"
openssl req -new -key ${SERVER_KEY} -out ${SERVER_CSR} -config ${CONFIG_FILE}

echo "### Signing the server CSR with the CA certificate"
openssl x509 -req -in ${SERVER_CSR} \
  -CA ${CA_CERT} -CAkey ${CA_KEY} -CAcreateserial -out ${SERVER_CERT} \
  -days 500 -sha256 \
  -extfile ${CONFIG_FILE} -extensions req_ext

cat ${SERVER_CERT} ${CA_CERT} > fullchain.crt

cat ${SERVER_CERT} ${CA_CERT} > fullchain.crt
cat ${CA_KEY} ${CA_CERT} > ca_combined.crt


[ -d ../MainService/crt ] || mkdir -p ../MainService/crt
cp ${CA_CERT} ../MainService/crt/${CA_CERT}
cp ${SERVER_CERT} ../MainService/crt/${SERVER_CERT}
cp ${SERVER_KEY} ../MainService/crt/${SERVER_KEY}

[ -d ../NotificationService/crt ] || mkdir -p ../NotificationService/crt
cp ${CA_CERT} ../NotificationService/crt/${CA_CERT}
cp ${SERVER_CERT} ../NotificationService/crt/${SERVER_CERT}
cp ${SERVER_KEY} ../NotificationService/crt/${SERVER_KEY}

[ -d ../Keycloak/crt ] || mkdir -p ../Keycloak/crt
cp ${CA_CERT} ../Keycloak/crt/${CA_CERT}
cp ${SERVER_CERT} ../Keycloak/crt/${SERVER_CERT}
cp ${SERVER_KEY} ../Keycloak/crt/${SERVER_KEY}

echo "### Certificate creation complete!"
echo "CA Certificate: ${CA_CERT}"
echo "Server Certificate: ${SERVER_CERT}"