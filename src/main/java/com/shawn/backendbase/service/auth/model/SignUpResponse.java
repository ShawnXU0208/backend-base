package com.shawn.backendbase.service.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Sign up response.
 */
@Getter
@Setter
@AllArgsConstructor
public class SignUpResponse {

  private String username;

  private String token;

}
