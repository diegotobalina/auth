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
public class FindAllScopesOutputDto {
  private String id;
  private String value;
  private String description;
  private String name;

  public FindAllScopesOutputDto(Scope scope) {
    this.id = scope.getId();
    this.value = scope.getValue();
    this.description = scope.getDescription();
    this.name = scope.getName();
  }
}
