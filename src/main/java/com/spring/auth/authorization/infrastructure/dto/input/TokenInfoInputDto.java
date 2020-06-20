package com.spring.auth.authorization.infrastructure.dto.input;

import com.spring.auth.anotations.validations.constraints.TokenConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TokenInfoInputDto {
  @TokenConstraint private String token;
}
