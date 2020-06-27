package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.in.LoginUserPort;
import com.spring.auth.authorization.infrastructure.dto.input.LoginInputDto;
import com.spring.auth.authorization.infrastructure.dto.output.LoginOutputDto;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.session.domain.Session;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class LoginController {

  private LoginUserPort loginUserPort;

  @ApiResponses(
      value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Invalid credentials")
      })
  @ApiOperation(
      value = "Login",
      notes = "Inicia sesión con el nombre de usuario o correo y la contraseña ")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/login")
  public LoginOutputDto login(@RequestBody @Valid final LoginInputDto loginInputDto)
      throws DuplicatedKeyException, NotFoundException, WrongPasswordException,
          LockedUserException {
    final String username = loginInputDto.getUsername();
    final String email = loginInputDto.getEmail();
    final String password = loginInputDto.getPassword();
    final Session session = loginUserPort.login(username, email, password);
    return new LoginOutputDto(session);
  }
}
