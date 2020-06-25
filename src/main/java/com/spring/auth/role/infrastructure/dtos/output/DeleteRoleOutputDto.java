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
public class DeleteRoleOutputDto {

  private String id;
  private String name;
  private String description;
  private String value;

  public DeleteRoleOutputDto(Role deletedRole) {
    this.id = deletedRole.getId();
    this.name = deletedRole.getName();
    this.description = deletedRole.getDescription();
    this.value = deletedRole.getValue();
  }
}
