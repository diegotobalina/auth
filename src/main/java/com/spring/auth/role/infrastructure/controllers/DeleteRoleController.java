package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.infrastructure.repositories.ports.DeleteRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.output.DeleteRoleOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotEmpty;

/** @author diegotobalina created on 24/06/2020 */
@RoleController
@AllArgsConstructor
@Validated
public class DeleteRoleController {

  private DeleteRolePort deleteRolePort;

  @ApiOperation(value = "Delete role", notes = "Borra un role de la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @DeleteMapping("/{roleId}")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','DELETE')")
  public DeleteRoleOutputDto delete(
      @PathVariable @NotEmpty String roleId) // todo: validate roleId format
      throws NotFoundException {
    Role deletedRole = deleteRolePort.delete(roleId);
    return new DeleteRoleOutputDto(deletedRole);
  }
}
