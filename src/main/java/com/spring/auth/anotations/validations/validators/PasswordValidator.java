package com.spring.auth.anotations.validations.validators;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import com.spring.auth.util.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/** @author diegotobalina created on 24/06/2020 */
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
