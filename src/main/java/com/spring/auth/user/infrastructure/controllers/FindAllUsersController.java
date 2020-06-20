package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.user.application.ports.out.FindAllUsersPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.output.FindAllUsersOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@UserController
@AllArgsConstructor
public class FindAllUsersController {

  private FindAllUsersPort findAllUsersPort;

  @ApiOperation(value = "Find all users", notes = "Trae todos los usuarios")
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
  public List<FindAllUsersOutputDto> findAll() {
    List<User> all = findAllUsersPort.findAll();
    return all.stream().map(FindAllUsersOutputDto::new).collect(Collectors.toList());
  }
}
