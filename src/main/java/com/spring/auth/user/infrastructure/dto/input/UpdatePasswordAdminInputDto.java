package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdatePasswordAdminInputDto {
  @PasswordConstraint private String newPassword; // new password for the user
}
