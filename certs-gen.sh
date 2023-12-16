ROOT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DB_PATH="$ROOT_PATH/database"
APP_PATH="$ROOT_PATH/src/main/resources"

##################################### User input password #################################################
echo "Enter the password for encrypting CA"
read CA_PASSPHRASE

echo "Enter the password for truststore of API application"
read APP_TRUSTSTORE_PASS

echo "Enter the password for keystore of API application"
read APP_KEYSTORE_PASS

echo "Enter the password for private key of API client"
read API_CLIENT_KEY_PASS

##################################### Clean existing certs #################################################
rm -rf $ROOT_PATH/ca
rm -rf $DB_PATH/certs
rm -rf $APP_PATH/certs

##################################### CA(Certificate Authority) #################################################
mkdir $ROOT_PATH/ca && cd $ROOT_PATH/ca
# generate a private key
openssl genrsa -out ca.key -aes128 -passout pass:${CA_PASSPHRASE} 2048
# generate a self-signed X.509 certificate for the CA using the created private key above
# can view the cert by decoding it: openssl x509 -in ca.cer -text -noout
openssl req -new -x509 -passin pass:${CA_PASSPHRASE} -days 3600 -key ca.key -out ca.cer -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domainCA.com"

##################################### Database #################################################
mkdir $DB_PATH/certs && cd $DB_PATH/certs
# generate a CSR to create server certificate for database
cp $ROOT_PATH/ca/ca.cer ca.cer
openssl genrsa 2048 > db-server.key
openssl req -new -key db-server.key -out db-server.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -passin pass:${CA_PASSPHRASE} -req -in db-server.csr -days 3600 -CA $ROOT_PATH/ca/ca.cer -CAkey $ROOT_PATH/ca/ca.key -set_serial 01 -out db-server.cer
rm db-server.csr

##################################### Application #################################################
# generate a CSR to create client certificate for application
mkdir $APP_PATH/certs && cd $APP_PATH/certs
openssl genrsa 2048 > spring-app.key
openssl req -new -key spring-app.key -out spring-app.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -passin pass:${CA_PASSPHRASE} -req -in spring-app.csr -days 3600 -CA $ROOT_PATH/ca/ca.cer -CAkey $ROOT_PATH/ca/ca.key -set_serial 01 -out spring-app.cer
# convert pem certs for app client to jks store
keytool -importcert -alias app-cert.jks -file $ROOT_PATH/ca/ca.cer -keystore truststore.jks -storepass ${APP_TRUSTSTORE_PASS} -noprompt
openssl pkcs12 -export -in spring-app.cer -inkey spring-app.key -out certificate.p12 -passout pass:${APP_KEYSTORE_PASS} -name "certificate"
keytool -importkeystore -srckeystore certificate.p12 -srcstoretype pkcs12 -srcstorepass ${APP_KEYSTORE_PASS} -destkeystore keystore.jks -deststorepass ${APP_KEYSTORE_PASS}
rm spring-app.csr && rm spring-app.key && rm spring-app.cer && rm certificate.p12

##################################### API Client #################################################
cd $ROOT_PATH/ca && mkdir client-certs && cd client-certs
openssl genrsa -out api-client.key -aes128 -passout pass:${API_CLIENT_KEY_PASS} 2048
openssl req -new -key api-client.key -out api-client.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -passin pass:${CA_PASSPHRASE} -req -in api-client.csr -days 3600 -CA $ROOT_PATH/ca/ca.cer -CAkey $ROOT_PATH/ca/ca.key -set_serial 01 -out api-client.cer

##################################### Output jdbc url of mysql connection #################################################
echo "The handy url of jdbc mysql connection url:"
echo "jdbc:mysql://host:port/db_name?"\
"sslMode=VERIFY_CA&"\
"trustCertificateKeyStoreUrl=file:${APP_PATH}/certs/truststore.jks&"\
"trustCertificateKeyStorePassword=${APP_TRUSTSTORE_PASS}&"\
"clientCertificateKeyStoreUrl=file:${APP_PATH}/certs/keystore.jks&"\
"clientCertificateKeyStorePassword=${APP_KEYSTORE_PASS}"