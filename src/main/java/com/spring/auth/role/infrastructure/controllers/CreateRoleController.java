package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CreateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.input.CreateRoleInputDto;
import com.spring.auth.role.infrastructure.dtos.output.CreateRoleOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RoleController
@AllArgsConstructor
public class CreateRoleController {

  private CreateRolePort createRolePort;

  @ApiOperation(value = "Create role", notes = "Crea un role en la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','CREATE')")
  public CreateRoleOutputDto create(@RequestBody @Valid CreateRoleInputDto createRoleInputDto)
      throws DuplicatedKeyException {
    String value = createRoleInputDto.getValue();
    String description = createRoleInputDto.getDescription();
    String name = createRoleInputDto.getName();
    Role role = new Role(name, description, value);
    Role createdRole = createRolePort.create(role);
    return new CreateRoleOutputDto(createdRole);
  }
}
