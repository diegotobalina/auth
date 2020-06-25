package com.spring.auth.scope.infrastructure.dtos.output;

import com.spring.auth.scope.domain.Scope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeleteScopeOutputDto {

  private String id;
  private String name;
  private String description;
  private String value;

  public DeleteScopeOutputDto(Scope createdScope) {
    this.id = createdScope.getId();
    this.name = createdScope.getName();
    this.description = createdScope.getDescription();
    this.value = createdScope.getValue();
  }
}
