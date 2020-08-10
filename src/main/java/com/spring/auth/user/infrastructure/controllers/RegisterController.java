package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.EmailDoesNotExistsException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.RegisterUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.RegisterInputDto;
import com.spring.auth.user.infrastructure.dto.output.RegisterOutputDto;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

/** @author diegotobalina created on 24/06/2020 */
@UserController
@AllArgsConstructor
public class RegisterController {

  private RegisterUserPort registerUserPort;

  @ApiOperation(value = "Register", notes = "Registra un nuevo usuario en la base de datos")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public RegisterOutputDto register(@RequestBody @Valid RegisterInputDto registerInputDto)
      throws DuplicatedKeyException, NotFoundException, EmailDoesNotExistsException {
    final String username = registerInputDto.getUsername();
    final String email = registerInputDto.getEmail();
    final String password = registerInputDto.getPassword();
    final User registeredUser = registerUserPort.register(username, email, password);
    return new RegisterOutputDto(registeredUser);
  }
}
