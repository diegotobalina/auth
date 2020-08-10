package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.AccessPort;
import com.spring.auth.authorization.infrastructure.dto.input.AccessInputDto;
import com.spring.auth.authorization.infrastructure.dto.output.AccessOutputDto;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class AccessController {

  private AccessPort accessPort;

  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(
      value = "Access",
      notes = "Obtiene un token de acceso mediante el token recibido en la llamada login")
  @PostMapping("/access")
  public AccessOutputDto access(@RequestBody @Valid AccessInputDto accessInputDto)
      throws NotFoundException, InvalidTokenException, LockedUserException {
    String token = TokenUtil.removeBearerPrefix(accessInputDto.getSession_token());
    List<String> roleValues = accessInputDto.getRoles();
    List<String> scopeValues = accessInputDto.getScopes();
    TokenUtil.JwtWrapper access = accessPort.access(token, roleValues, scopeValues);
    return new AccessOutputDto(access);
  }
}
