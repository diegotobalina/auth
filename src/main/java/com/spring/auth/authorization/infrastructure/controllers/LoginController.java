package com.spring.auth.authorization.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.AuthorizationController;
import com.spring.auth.authorization.application.ports.AccessPort;
import com.spring.auth.authorization.application.ports.LoginUserPort;
import com.spring.auth.authorization.infrastructure.dto.input.LoginInputDto;
import com.spring.auth.authorization.infrastructure.dto.output.LoginOutputDto;
import com.spring.auth.session.domain.Session;
import com.spring.auth.util.TokenUtil;
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
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@AllArgsConstructor
@AuthorizationController
public class LoginController {

  private LoginUserPort loginUserPort;
  private AccessPort accessPort;

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
      throws Exception {
    final String username = loginInputDto.getUsername();
    final String email = loginInputDto.getEmail();
    final String password = loginInputDto.getPassword();
    final Session session = loginUserPort.login(username, email, password);
    List<String> roleValues = loginInputDto.getRoles();
    List<String> scopeValues = loginInputDto.getScopes();
    final TokenUtil.JwtWrapper access =
        accessPort.access(session.getToken(), roleValues, scopeValues);
    return new LoginOutputDto(session, access);
  }
}
