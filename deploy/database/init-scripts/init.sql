-- Require client authentication to ensure user present a certificate from our CA
ALTER USER 'user'@'%' REQUIRE X509;