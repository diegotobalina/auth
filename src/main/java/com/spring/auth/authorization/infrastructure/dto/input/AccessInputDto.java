package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.SessionTokenConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccessInputDto {
  @SessionTokenConstraint private String token;
}
