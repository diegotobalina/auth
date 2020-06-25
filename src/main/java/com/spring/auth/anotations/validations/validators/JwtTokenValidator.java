package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.JwtTokenConstraint;
import com.spring.auth.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author diegotobalina created on 24/06/2020 */
public class JwtTokenValidator implements ConstraintValidator<JwtTokenConstraint, String> {

  @Override
  public void initialize(final JwtTokenConstraint jwt) {
    // empty
  }

  @Override
  public boolean isValid(final String jwt, final ConstraintValidatorContext cxt) {
    return RegexUtil.isBearerJwt(jwt);
  }
}
