package com.shawn.backendbase.utils.constraint;

import com.shawn.backendbase.data.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

/**
 * The validator for {@link RolesConstraint}.
 */
public class RolesConstraintValidator implements ConstraintValidator<RolesConstraint, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    return Stream.of(Role.values()).anyMatch(role -> role.name().equals(value));
  }
}
