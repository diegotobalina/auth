package com.spring.auth.scope.infrastructure.dtos.output;

import com.spring.auth.scope.domain.Scope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateScopeOutputDto {

  private String id;
  private String name;
  private String description;
  private String value;

  public CreateScopeOutputDto(Scope createdScope) {
    this.id = createdScope.getId();
    this.name = createdScope.getName();
    this.description = createdScope.getDescription();
    this.value = createdScope.getValue();
  }
}
