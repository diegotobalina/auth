package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.input.RemoveScopesToRoleInputDto;
import com.spring.auth.role.infrastructure.dtos.output.RemoveScopesToRoleOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@RoleController
@AllArgsConstructor
@Validated
public class RemoveScopesToRoleController {

  private RemoveScopesFromRolePort removeScopesFromRolePort;

  @ApiOperation(value = "Remove Scopes", notes = "Quita scopes a un role")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @DeleteMapping("/{roleId}/scopes")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','DELETE')")
  public RemoveScopesToRoleOutputDto add(
      @PathVariable @NotEmpty String roleId, // todo: validate roleId format
      @RequestBody @Valid RemoveScopesToRoleInputDto removeScopesToRoleInputDto)
      throws DuplicatedKeyException, NotFoundException {
    List<String> scopes = removeScopesToRoleInputDto.getScopes();
    Role roleWithoutScopes = removeScopesFromRolePort.remove(roleId, scopes);
    return new RemoveScopesToRoleOutputDto(roleWithoutScopes);
  }
}
