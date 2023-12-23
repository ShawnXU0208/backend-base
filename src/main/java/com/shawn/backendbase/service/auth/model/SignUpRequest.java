package com.shawn.backendbase.service.auth.model;

import com.shawn.backendbase.configuration.RegexPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

  @NotBlank(message = "The account name is required")
  @Pattern(regexp = RegexPatterns.USERNAME, message = "The account name is not valid.")
  private String accountName;

  @NotBlank(message = "The email is required")
  @Email(message = "The provided email is not valid")
  private String email;

  @NotBlank(message = "The password is required")
  @Pattern(regexp = RegexPatterns.PASSWORD, message = "The password is not valid.")
  private String password;

  @NotBlank(message = "The role is required")
  private String role;

}
