package com.spring.auth.role.infrastructure.dtos.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RemoveScopesToRoleInputDto {
  @NotEmpty private List<String> scopes; // todo: validate scopes format
}
