package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.AuthorizePort;
import com.spring.auth.authorization.infrastructure.dto.input.LoginInputDto;
import com.spring.auth.authorization.infrastructure.dto.output.AuthorizeOutputDto;
import com.spring.auth.exceptions.application.*;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class AuthorizeController {

  private AuthorizePort authorizePort;

  @ApiOperation(
      value = "Authorize",
      notes = "Genera un token de acceso para las aplicaciones externas")
  @PostMapping("/authorize")
  public AuthorizeOutputDto callback(
      @RequestParam(value = "client_id") String clientId,
      @RequestParam(value = "origin_uri") String originUrl,
      @RequestParam(value = "response_type", defaultValue = "id_token") String responseType,
      @RequestParam(value = "resource", defaultValue = "auth") String resource,
      @RequestParam(value = "redirect_uri") String callbackUrl,
      @RequestBody @Valid LoginInputDto loginInputDto)
      throws NotFoundException, DuplicatedKeyException, WrongPasswordException, LockedUserException,
          InvalidTokenException, InvalidAuthorizeParamsException {
    String email = loginInputDto.getEmail();
    String username = loginInputDto.getUsername();
    String password = loginInputDto.getPassword();
    List<String> roleValues = loginInputDto.getRoles();
    List<String> scopeValues = loginInputDto.getScopes();
    String token =
        authorizePort.process(
            clientId,
            originUrl,
            callbackUrl,
            resource,
            responseType,
            username,
            email,
            password,
            roleValues,
            scopeValues);
    return new AuthorizeOutputDto(token);
  }
}
