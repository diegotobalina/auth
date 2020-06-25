package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.user.application.ports.in.UpdateUserPasswordPort;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordAdminInputDto;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordInputDto;
import com.spring.auth.util.UserUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.security.Principal;

@UserController
@AllArgsConstructor
public class UpdateUserPasswordController {

  private UpdateUserPasswordPort updateUserPasswordPort;

  /**
   * Update user password
   *
   * @param principal Logged user
   * @param updatePasswordInputDto Needed data for the password update
   * @throws DuplicatedKeyException If there was some problem updating the user, like duplicated
   *     email
   * @throws NotFoundException If the user that needs the password update was not found
   * @throws WrongPasswordException If the old password was incorrect
   */
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update password", notes = "Actualiza tu contraseña")
  @PutMapping("/password")
  @PreAuthorize(
      "(hasRole('ADMIN') or hasRole('USER')) and hasPermission('hasAccess','UPDATE_USER')")
  public void updatePassword(
      final Principal principal, @RequestBody @Valid UpdatePasswordInputDto updatePasswordInputDto)
      throws DuplicatedKeyException, NotFoundException, WrongPasswordException {
    String userIdFromPrincipal = UserUtil.getUserIdFromPrincipal(principal);
    String oldPassword = updatePasswordInputDto.getOldPassword();
    String newPassword = updatePasswordInputDto.getNewPassword();
    updateUserPasswordPort.update(userIdFromPrincipal, oldPassword, newPassword);
  }

  /**
   * Update user password, old password is not checked so this method should only be for admins
   *
   * @param userId User that need the password update
   * @param updatePasswordAdminInputDto Needed data for the password update
   * @throws DuplicatedKeyException If there was some problem updating the user, like duplicated
   *     username
   * @throws NotFoundException If the user that needs the password update was not found
   */
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update password", notes = "Actualiza la contraseña de un usuario")
  @PutMapping("/{userId}/password")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','UPDATE')")
  public void updatePassword(
      @PathVariable String userId,
      @RequestBody @Valid UpdatePasswordAdminInputDto updatePasswordAdminInputDto)
      throws DuplicatedKeyException, NotFoundException {
    String newPassword = updatePasswordAdminInputDto.getNewPassword();
    updateUserPasswordPort.update(userId, newPassword);
  }
}
