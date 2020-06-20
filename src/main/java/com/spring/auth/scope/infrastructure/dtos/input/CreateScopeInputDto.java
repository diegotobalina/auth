package com.spring.auth.scope.infrastructure.dtos.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateScopeInputDto {
  @NotEmpty private String name; // todo: validate name format
  @NotEmpty private String description; // todo: validate description format
  @NotEmpty private String value; // todo: validate value format
}
