package com.shawn.backendbase.service.auth;

import com.shawn.backendbase.controller.Exception.DuplicatedKeyException;
import com.shawn.backendbase.data.User;
import com.shawn.backendbase.data.UserRepository;
import com.shawn.backendbase.service.auth.model.SignInRequest;
import com.shawn.backendbase.service.auth.model.SignInResponse;
import com.shawn.backendbase.service.auth.model.SignUpRequest;
import com.shawn.backendbase.service.auth.model.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  @Autowired
  public AuthService(final AuthenticationManager authenticationManager, final UserRepository userRepository, final PasswordEncoder passwordEncoder,
                     final JwtService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  /**
   * Sign up service method, create a new user if request is valid.
   *
   * @param request {@link SignUpRequest}
   * @return {@link SignUpResponse}
   */
  public SignUpResponse signUp(final SignUpRequest request) {
    final User user = new User();
    user.setAccountName(request.getAccountName());
    user.setEmail(request.getEmail());
    user.setRole(request.getRole());
    user.setPassword(this.passwordEncoder.encode(request.getPassword()));

    try {
      final User newUser = this.userRepository.save(user);
      return new SignUpResponse(user.getAccountName(), this.jwtService.generateToken(newUser));
    } catch (DataIntegrityViolationException e) {
      throw new DuplicatedKeyException(DuplicatedKeyException.refactoredMsg(e));
    }
  }

  /**
   * Sign in service method, make use of {@link AuthenticationManager} for log in authentication process.
   *
   * @param request {@link SignInRequest}
   * @return {@link SignInResponse}
   */
  public SignInResponse signIn(final SignInRequest request) {
    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    final Authentication auth = authenticationManager.authenticate(authToken);
    final User user = this.userRepository.findByAccountName(request.getUsername()).get();
    return new SignInResponse(auth.getName(), this.jwtService.generateToken(user));
  }
}
