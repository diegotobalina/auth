package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.SessionTokenConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LogoutInputDto {
  @SessionTokenConstraint private String token;
}
