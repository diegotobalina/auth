package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.in.TokenInfoPort;
import com.spring.auth.authorization.domain.TokenInfo;
import com.spring.auth.authorization.infrastructure.dto.output.TokenInfoOutputDto;
import com.spring.auth.exceptions.application.GoogleGetInfoException;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.UnknownTokenFormatException;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Validated
@AllArgsConstructor
@AuthorizationController
public class TokenInfoController {

  private TokenInfoPort tokenInfoPort;

  @ApiOperation(
      value = "Token info",
      notes =
          "Devuelve la información relacionada con un token, puede ser de sesión, de acceso o de google")
  @GetMapping("/tokenInfo")
  public TokenInfoOutputDto tokenInfo(
      @RequestParam @NotEmpty final String token) // todo: validate param
      throws NotFoundException, UnknownTokenFormatException, InvalidTokenException,
          GeneralSecurityException, IOException, GoogleGetInfoException {
    final TokenInfo tokenInfo = tokenInfoPort.tokenInfo(token);
    return new TokenInfoOutputDto(tokenInfo);
  }
}
