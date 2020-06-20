package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.role.application.ports.out.FindAllRolesPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.output.FindAllRolesOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RoleController
@AllArgsConstructor
public class FindAllRolesController {

  private FindAllRolesPort findAllRolesPort;

  @ApiOperation(value = "Find all roles", notes = "Trae todos los roles de la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @GetMapping
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','READ')")
  public List<FindAllRolesOutputDto> findAll() {
    List<Role> allRoles = findAllRolesPort.findAll();
    return allRoles.stream().map(FindAllRolesOutputDto::new).collect(Collectors.toList());
  }
}
