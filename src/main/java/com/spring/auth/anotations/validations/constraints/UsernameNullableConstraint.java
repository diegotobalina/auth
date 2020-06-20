package com.spring.auth.anotations.validations.constraints;

import com.spring.auth.anotations.validations.validators.UsernameNullableValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameNullableValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UsernameNullableConstraint {
  String message() default "invalid username format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
