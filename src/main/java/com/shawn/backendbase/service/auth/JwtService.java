package com.shawn.backendbase.service.auth;

import com.shawn.backendbase.data.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${app.jwt.key}")
  private String secretKey;

  @Value("${app.jwt.expire.days}")
  private int expireDays;

  @Value("${app.domain}")
  private String issuer;

  public Claims extractAllClaims(String token) {
    try {
      return Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token).getPayload();
    } catch (Exception e) {
      return null;
    }
  }

  public boolean isTokenClaimsValid(final Claims claims) {
    // check if token is expired
    return claims.getExpiration().after(new Date());
  }

  public String generateToken(final User user) {
    return Jwts.builder()
        .subject(user.getUsername())
        .claim("roles", user.getAuthorities())
        .claim("userID", user.getId())
        .issuer(this.issuer)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + (long) expireDays * 24 * 60 * 60 * 1000))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * generate key based on configured key.
   * hmacShaKeyFor() method determines the Algorithm to be used based on the bit length of key string.
   *
   * @return key
   */
  private Key getSigningKey() {
    byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private SecretKeySpec getSecretKey() {
    return new SecretKeySpec(this.secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
  }
}
