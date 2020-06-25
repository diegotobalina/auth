package com.spring.auth.role.infrastructure.dtos.output;

import com.spring.auth.role.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CreateRoleOutputDto {

  private String id;
  private String name;
  private String description;
  private String value;

  public CreateRoleOutputDto(Role createdRole) {
    this.id = createdRole.getId();
    this.name = createdRole.getName();
    this.description = createdRole.getDescription();
    this.value = createdRole.getValue();
  }
}
