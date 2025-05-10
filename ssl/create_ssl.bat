@echo off
REM generate_cert.bat
REM This script creates a CA, generates a server key and CSR (with SANs defined in openssl-san.cnf),
REM and then uses the CA to sign the CSR to produce a signed certificate.
REM Adjust subject details and file paths as needed.

SETLOCAL ENABLEDELAYEDEXPANSION

REM Define filenames
set "CA_KEY=rootCA.key"
set "CA_CERT=rootCA.crt"
set "CA_SERIAL=rootCA.srl"
set "SERVER_KEY=domain.key"
set "SERVER_CSR=domain.csr"
set "SERVER_CERT=domain.crt"
set "CONFIG_FILE=openssl-san.cnf"

echo.
echo ### Generating CA private key
openssl genrsa -out %CA_KEY% 4096
IF ERRORLEVEL 1 (
  echo Error generating CA key.
  goto end
)

echo.
echo ### Generating CA certificate
REM Adjust the subject details as needed.
openssl req -x509 -new -nodes -key %CA_KEY% -sha256 -days 1024 -out %CA_CERT% -subj "/C=US/ST=YourState/L=YourCity/O=YourOrg/OU=YourUnit/CN=MyCA"
IF ERRORLEVEL 1 (
  echo Error generating CA certificate.
  goto end
)

echo.
echo ### Generating server private key...
openssl genrsa -out %SERVER_KEY% 2048
IF ERRORLEVEL 1 (
  echo Error generating server key.
  goto end
)

echo ### Generating server CSR using %CONFIG_FILE%
openssl req -new -key %SERVER_KEY% -out %SERVER_CSR% -config %CONFIG_FILE%
IF ERRORLEVEL 1 (
  echo Error generating server CSR.
  goto end
)

echo ### Signing the server CSR with the CA certificate
openssl x509 -req -in %SERVER_CSR% -CA %CA_CERT% -CAkey %CA_KEY% -CAcreateserial -out %SERVER_CERT% -days 500 -sha256 -extfile %CONFIG_FILE% -extensions req_ext
IF ERRORLEVEL 1 (
  echo Error signing the server CSR.
  goto end
)

cat %CA_KEY% %CA_CERT% > fullchain.crt

echo.
echo ### Certificate creation complete!
echo CA Certificate: %CA_CERT%
echo Server Certificate: %SERVER_CERT%

:end
pause
ENDLOCAL