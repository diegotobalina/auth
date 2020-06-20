package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import com.spring.auth.shared.util.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

  @Override
  public void initialize(final PasswordConstraint contactNumber) {
    // empty
  }

  @Override
  public boolean isValid(final String password, final ConstraintValidatorContext cxt) {
    if (StringUtils.isBlank(password)) return false;
    return RegexUtil.isValidPassword(password);
  }
}
