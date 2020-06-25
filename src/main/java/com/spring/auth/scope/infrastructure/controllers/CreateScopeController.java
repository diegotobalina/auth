package com.spring.auth.scope.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ScopeController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.scope.application.ports.out.CreateScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.dtos.input.CreateScopeInputDto;
import com.spring.auth.scope.infrastructure.dtos.output.CreateScopeOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

/** @author diegotobalina created on 24/06/2020 */
@ScopeController
@AllArgsConstructor
public class CreateScopeController {

  private CreateScopePort createScopePort;

  @ApiOperation(value = "Create scope", notes = "Crea una scope en la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','CREATE')")
  public CreateScopeOutputDto create(@RequestBody @Valid CreateScopeInputDto createScopeInputDto)
      throws DuplicatedKeyException {
    final String name = createScopeInputDto.getName();
    final String value = createScopeInputDto.getValue();
    final String description = createScopeInputDto.getDescription();
    final Scope scope = new Scope(name, description, value);
    final Scope createdScope = createScopePort.create(scope);
    return new CreateScopeOutputDto(createdScope);
  }
}
