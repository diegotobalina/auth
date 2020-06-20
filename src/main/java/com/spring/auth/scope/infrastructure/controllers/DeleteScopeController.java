package com.spring.auth.scope.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ScopeController;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.application.ports.out.DeleteScopePort;
import com.spring.auth.scope.application.ports.out.FindScopeByIdPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.dtos.output.DeleteScopeOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotEmpty;

@ScopeController
@AllArgsConstructor
@Validated
public class DeleteScopeController {

  private DeleteScopePort deleteScopePort;
  private FindScopeByIdPort findScopeByIdPort;

  @ApiOperation(value = "Delete scope", notes = "Borra una scope de la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @DeleteMapping("/{scopeId}")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','DELETE')")
  public DeleteScopeOutputDto create(
      @PathVariable @NotEmpty String scopeId) // todo: validate scopeId format
      throws NotFoundException {
    Scope deletedScope = deleteScopePort.delete(scopeId);
    return new DeleteScopeOutputDto(deletedScope);
  }
}
