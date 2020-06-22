package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.SessionTokenConstraint;
import com.spring.auth.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SessionTokenValidator implements ConstraintValidator<SessionTokenConstraint, String> {

  @Override
  public void initialize(final SessionTokenConstraint sessionToken) {
    // empty
  }

  @Override
  public boolean isValid(final String sessionToken, final ConstraintValidatorContext cxt) {
    return RegexUtil.isSessionToken(sessionToken);
  }
}
