package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import com.spring.auth.anotations.validations.constraints.UsernameConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegisterInputDto {
  @UsernameConstraint private String username; // username for the new user
  @Email @NotNull private String email; // email for the new user
  @PasswordConstraint private String password; // password for the new user
}
