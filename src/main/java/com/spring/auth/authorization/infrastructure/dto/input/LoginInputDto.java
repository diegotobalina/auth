package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import com.spring.auth.anotations.validations.constraints.UsernameNullableConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginInputDto {
  @UsernameNullableConstraint private String username;
  @Email private String email;
  @PasswordConstraint private String password;
}
