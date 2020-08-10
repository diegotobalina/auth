package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.UserInfoPort;
import com.spring.auth.authorization.infrastructure.dto.output.UserInfoOutPutDto;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class UserInfoController {

  private UserInfoPort userInfoPort;

  @ApiOperation(
      value = "User info",
      notes =
          "Devuelve la información de un usuario que ha iniciado sesión con un token, puede ser de cualquier token")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @GetMapping("/userInfo")
  @PreAuthorize("(hasRole('ADMIN') or hasRole('USER')) and hasPermission('hasAccess','READ_USER')")
  public UserInfoOutPutDto userInfo(final Principal principal)
      throws NotFoundException, LockedUserException {
    final User user = userInfoPort.userInfo(principal);
    return new UserInfoOutPutDto(user);
  }
}
