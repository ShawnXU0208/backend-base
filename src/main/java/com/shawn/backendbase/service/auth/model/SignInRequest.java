package com.shawn.backendbase.service.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

  @NotBlank(message = "The username is required.")
  private String username;

  @NotBlank(message = "The password is required")
  private String password;

}
