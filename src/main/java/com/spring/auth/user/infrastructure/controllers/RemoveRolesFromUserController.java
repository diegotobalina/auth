package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.in.RemoveRolesFromUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.AddRolesToUserInputDto;
import com.spring.auth.user.infrastructure.dto.output.AddRolesToUserOutputDto;
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

@UserController
@AllArgsConstructor
@Validated
public class RemoveRolesFromUserController {

  private RemoveRolesFromUserPort removeRolesFromUserPort;

  /**
   * Remove roles from the user
   *
   * @param userId Id of the user that will lose the roles
   * @param addRolesToUserInputDto Needed data for the role removal
   * @return Updates user
   * @throws DuplicatedKeyException If there was some problem saving the user
   * @throws NotFoundException If the user was not found by the id
   */
  @ApiOperation(value = "Remove roles from the user", notes = "Quita roles a un usuario")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @DeleteMapping("/{userId}/roles")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','DELETE')")
  public AddRolesToUserOutputDto findAll(
      @PathVariable @NotEmpty String userId, // todo: validate userId format
      @RequestBody @Valid AddRolesToUserInputDto addRolesToUserInputDto)
      throws DuplicatedKeyException, NotFoundException {
    List<String> roleIds = addRolesToUserInputDto.getRoleIds();
    User updatedUser = removeRolesFromUserPort.remove(userId, roleIds);
    return new AddRolesToUserOutputDto(updatedUser);
  }
}
