package com.spring.auth.role.infrastructure.dtos.output;

import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RemoveScopesToRoleOutputDto {

  private String id;
  private String name;
  private String description;
  private String value;
  private List<String> scopes = new ArrayList<>();

  public RemoveScopesToRoleOutputDto(Role role) {
    this.id = role.getId();
    this.name = role.getName();
    this.description = role.getDescription();
    this.value = role.getValue();
    for (Scope scope : role.getScopes()) {
      this.scopes.add(scope.getId());
    }
  }
}
