package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.SessionTokenConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessInputDto {
  @SessionTokenConstraint private String session_token;

  @ApiModelProperty(example = "[ROLE_ADMIN]")
  private List<String> roles;

  @ApiModelProperty(example = "[READ,CREATE,UPDATE,DELETE]")
  private List<String> scopes;
}
