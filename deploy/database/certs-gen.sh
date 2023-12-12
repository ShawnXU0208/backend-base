rm -rf certs
mkdir certs && cd certs

# generate a private key
openssl genrsa 2048 > ca.key
# generate a self-signed X.509 certificate for the CA(Certificate Authority) using the created private key above
# can view the cert by decoding it: openssl x509 -in ca.cer -text -noout
openssl req -new -x509 -nodes -days 3600 -key ca.key -out ca.cer -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domainCA.com"

# generate a CSR to create server certificate
openssl genrsa 2048 > server.key
openssl req -new -key server.key -out server.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -req -in server.csr -days 3600 -CA ca.cer -CAkey ca.key -set_serial 01 -out server.cer

# generate a CSR to create client certificate
openssl genrsa 2048 > client.key
openssl req -new -key client.key -out client.csr -subj "/C=NZ/ST=Canterbury/L=Christchurch/O=My Company/OU=IT/CN=my-domain.com"
openssl x509 -req -in client.csr -days 3600 -CA ca.cer -CAkey ca.key -set_serial 01 -out client.cer

# remove unneeded CSR
rm server.csr
rm client.csr