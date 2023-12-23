package com.shawn.backendbase.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${app.jwt.key}")
  private String secretKey;

  @Value("${app.jwt.expire.days}")
  private int expireDays;

  public String generateToken(final UserDetails user) {
    return "";
  }
}
