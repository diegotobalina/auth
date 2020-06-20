package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.in.AddRolesToUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.AddRolesToUserInputDto;
import com.spring.auth.user.infrastructure.dto.output.AddRolesToUserOutputDto;
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

@UserController
@AllArgsConstructor
@Validated
public class AddRolesToUserController {

  private AddRolesToUserPort addRolesToUserPort;

  @ApiOperation(value = "Add roles to user", notes = "Añade roles a un usuario")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{userId}/roles")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','UPDATE')")
  public AddRolesToUserOutputDto findAll(
      @PathVariable @NotEmpty String userId, // todo: validate userId format
      @RequestBody @Valid AddRolesToUserInputDto addRolesToUserInputDto)
      throws DuplicatedKeyException, NotFoundException {
    List<String> roleIds = addRolesToUserInputDto.getRoleIds();
    User updatedUser = addRolesToUserPort.add(userId, roleIds);
    return new AddRolesToUserOutputDto(updatedUser);
  }
}
