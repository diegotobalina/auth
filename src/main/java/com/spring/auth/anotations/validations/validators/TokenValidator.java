package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.TokenConstraint;
import com.spring.auth.util.RegexUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author diegotobalina created on 24/06/2020 */
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
