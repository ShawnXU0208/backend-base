package com.shawn.backendbase.utils.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The customised annotation for validating if a string field is in configured roles.
 * see {@link RolesConstraintValidator for validation details}
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = RolesConstraintValidator.class)
public @interface RolesConstraint {

  /**
   * Returns the error message to be shown when the input string value is not one of the
   * valid roles. The default value is "Invalid role".
   */
  String message() default "Invalid role";

  /**
   * Returns the validation groups targeted by this constraint.
   * By default, the constraint targets all groups.
   */
  Class<?>[] groups() default {};

  /**
   * Returns the additional payloads associated with the constraint.
   * By default, there are no additional payloads.
   */
  Class<? extends Payload>[] payload() default {};
}
