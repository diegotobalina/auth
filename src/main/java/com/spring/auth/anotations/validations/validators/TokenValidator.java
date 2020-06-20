package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.TokenConstraint;
import com.spring.auth.shared.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TokenValidator implements ConstraintValidator<TokenConstraint, String> {

  @Override
  public void initialize(final TokenConstraint token) {
    // empty
  }

  @Override
  public boolean isValid(final String token, final ConstraintValidatorContext cxt) {
    return RegexUtil.isBearerJwt(token)
        || RegexUtil.isSessionToken(token)
        || RegexUtil.isGoogleJwt(token);
  }
}
