package com.spring.auth.anotations.validations.constraints;

import com.spring.auth.anotations.validations.validators.TokenValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/** @author diegotobalina created on 24/06/2020 */
@Documented
@Constraint(validatedBy = TokenValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TokenConstraint {
  String message() default "invalid token format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
