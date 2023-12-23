package com.shawn.backendbase.controller;

import com.shawn.backendbase.service.auth.AuthService;
import com.shawn.backendbase.service.auth.model.SignInRequest;
import com.shawn.backendbase.service.auth.model.SignInResponse;
import com.shawn.backendbase.service.auth.model.SignUpRequest;
import com.shawn.backendbase.service.auth.model.SignUpResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserRestController {

  private final AuthService authService;

  @Autowired
  public UserRestController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(path = "signup")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
    return ResponseEntity.ok(this.authService.signUp(request));
  }

  @PostMapping(path = "signin")
  public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
    return ResponseEntity.ok(this.authService.signIn(request));
  }
}
