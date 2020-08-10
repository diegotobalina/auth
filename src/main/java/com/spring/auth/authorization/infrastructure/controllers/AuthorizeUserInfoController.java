package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.UserInfoPort;
import com.spring.auth.authorization.infrastructure.dto.output.UserInfoOutPutDto;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class AuthorizeUserInfoController {

  private UserInfoPort userInfoPort;

  @ApiOperation(
      value = "User info",
      notes = "Devuelve la información de un usuario autorizado ( cualquier token valido )")
  @GetMapping("/authorize/userInfo")
  public UserInfoOutPutDto userInfo(
      @RequestParam(value = "client_id", required = false) String clientId,
      @RequestParam @NotEmpty final String token)
      throws NotFoundException, LockedUserException, GeneralSecurityException,
          InvalidTokenException, IOException, UnknownTokenFormatException,
          EmailDoesNotExistsException, GoogleGetInfoException, InfiniteLoopException,
          DuplicatedKeyException {
    final User user = userInfoPort.userInfo(token, clientId);
    return new UserInfoOutPutDto(user);
  }
}
