package com.shawn.backendbase.configuration;

import com.shawn.backendbase.service.auth.JwtService;
import com.shawn.backendbase.service.auth.UserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String JWT_TOKEN_PREFIX = "Bearer ";
  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @Autowired
  public JwtAuthenticationFilter(final JwtService jwtService, final UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // check if token exists in header
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith(JWT_TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    // validate the token
    final String token = authHeader.substring(7);
    final Claims claims = this.jwtService.extractAllClaims(token);
    if (claims == null || !this.jwtService.isTokenClaimsValid(claims)) {
      filterChain.doFilter(request, response);
      return;
    }

    // token is valid
    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(claims.getSubject());
    final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    // tell the Authentication object more details about Http request
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    // set authentication information to security context holder
    final SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    SecurityContextHolder.setContext(context);

    // continue with authenticated user
    filterChain.doFilter(request, response);
  }
}
