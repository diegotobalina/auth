package com.spring.auth.role.infrastructure.dtos.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateRoleInputDto {
  @NotNull private String name; // todo: validate name format
  @NotNull private String description; // todo: validate description format
  @NotNull private String value; // todo: validate value format
}
