package com.shawn.backendbase.utils.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * The validator for {@link StringConstraint}.
 */
public class StringConstraintValidator implements ConstraintValidator<StringConstraint, String> {

  private String acceptedValues;

  @Override
  public void initialize(StringConstraint constraintAnnotation) {
    this.acceptedValues = constraintAnnotation.acceptValues();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    final List<String> acceptValuesList = Arrays.asList(acceptedValues.split(","));
    return acceptValuesList.contains(value);
  }
}
