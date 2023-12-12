ROOT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DB_PATH="$ROOT_PATH/database"
APP_PATH="$ROOT_PATH/application"

##################################### Clean existing certs #################################################
rm -rf $ROOT_PATH/certs
rm -rf $DB_PATH/certs
rm -rf $APP_PATH/certs

##################################### CA(Certificate Authority) #################################################
mkdir $ROOT_PATH/certs && cd $ROOT_PATH/certs
# generate a private key
openssl genrsa 2048 > ca.key
# generate a self-signed X.509 certificate for the CA using the created private key above
# can view the cert by decoding it: openssl x509 -in ca.cer -text -noout
openssl req -new -x509 -nodes -days 3600 -key ca.key -out ca.cer -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domainCA.com"

##################################### Database #################################################
mkdir $DB_PATH/certs && cd $DB_PATH/certs
# generate a CSR to create server certificate for database
cp $ROOT_PATH/certs/ca.cer ca.cer
openssl genrsa 2048 > db-server.key
openssl req -new -key db-server.key -out db-server.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -req -in db-server.csr -days 3600 -CA $ROOT_PATH/certs/ca.cer -CAkey $ROOT_PATH/certs/ca.key -set_serial 01 -out db-server.cer
rm db-server.csr

# generate a CSR to create client certificate for application
mkdir $APP_PATH/certs && cd $APP_PATH/certs
cp $ROOT_PATH/certs/ca.cer ca.cer
openssl genrsa 2048 > app-client.key
openssl req -new -key app-client.key -out app-client.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -req -in app-client.csr -days 3600 -CA $ROOT_PATH/certs/ca.cer -CAkey $ROOT_PATH/certs/ca.key -set_serial 01 -out app-client.cer
rm app-client.csr