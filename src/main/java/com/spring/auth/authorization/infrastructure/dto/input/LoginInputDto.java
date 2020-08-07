package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.PasswordConstraint;
import com.spring.auth.anotations.validations.constraints.UsernameNullableConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginInputDto {
  @UsernameNullableConstraint
  @ApiModelProperty(example = "admin")
  private String username;

  @Email
  @ApiModelProperty(example = "admin@admin.com")
  private String email;

  @PasswordConstraint
  @ApiModelProperty(example = "string")
  private String password;

  @ApiModelProperty(example = "[ROLE_ADMIN]")
  private List<String> roles;

  @ApiModelProperty(example = "[READ,CREATE,UPDATE,DELETE]")
  private List<String> scopes;
}
