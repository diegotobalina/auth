package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.in.AddScopesToRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.input.AddScopesToRoleInputDto;
import com.spring.auth.role.infrastructure.dtos.output.AddScopesToRoleOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RoleController
@AllArgsConstructor
@Validated
public class AddScopesToRoleController {

  private AddScopesToRolePort addScopesToRolePort;

  @ApiOperation(value = "Add Scopes", notes = "Añade scopes a un role")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{roleId}/scopes")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','UPDATE')")
  public AddScopesToRoleOutputDto add(
      @PathVariable @NotEmpty String roleId, // todo: validate roleId format
      @RequestBody @Valid AddScopesToRoleInputDto addScopesToRoleInputDto)
      throws DuplicatedKeyException, NotFoundException {
    List<String> scopes = addScopesToRoleInputDto.getScopes();
    Role roleWithScopes = addScopesToRolePort.add(roleId, scopes);
    return new AddScopesToRoleOutputDto(roleWithScopes);
  }
}
