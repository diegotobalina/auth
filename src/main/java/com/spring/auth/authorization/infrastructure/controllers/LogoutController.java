package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.in.LogoutUserPort;
import com.spring.auth.authorization.infrastructure.dto.input.LogoutInputDto;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.shared.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@AuthorizationController
public class LogoutController {

  private LogoutUserPort logoutUserPort;

  @DeleteMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(@RequestBody @Valid final LogoutInputDto logoutInputDto)
      throws NotFoundException {
    final String token = TokenUtil.removeBearerPrefix(logoutInputDto.getToken());
    logoutUserPort.logout(token);
  }
}
