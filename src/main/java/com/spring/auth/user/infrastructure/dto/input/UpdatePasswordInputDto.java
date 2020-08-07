package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@ToString
@NoArgsConstructor
public class UpdatePasswordInputDto {
  @PasswordConstraint private String old_password; // old password of the user
  @PasswordConstraint private String new_password; // new password for the user
}
