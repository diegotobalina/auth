package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.UsernameConstraint;
import com.spring.auth.shared.util.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public void initialize(final UsernameConstraint contactNumber) {
    // empty
  }

  @Override
  public boolean isValid(final String username, final ConstraintValidatorContext cxt) {
    if (StringUtils.isBlank(username)) return false;
    return RegexUtil.isValidUsername(username);
  }
}
