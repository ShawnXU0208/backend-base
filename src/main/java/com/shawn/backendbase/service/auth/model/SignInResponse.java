package com.shawn.backendbase.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Sign in response.
 */
@Getter
@Setter
@AllArgsConstructor
public class SignInResponse {

  private String username;

  private String token;

}
