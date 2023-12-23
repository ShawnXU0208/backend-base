package com.shawn.backendbase.service.auth.model;

import com.shawn.backendbase.configuration.RegexPatterns;
import com.shawn.backendbase.data.Role;
import com.shawn.backendbase.utils.constraint.RolesConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

  @NotEmpty(message = "The role is required")
  @RolesConstraint
  private String role;

  public Role getRole() {
    return Role.valueOf(this.role);
  }
}
