package com.spring.auth.scope.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ScopeController;
import com.spring.auth.scope.application.ports.out.FindAllScopesPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.dtos.output.FindAllScopesOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@ScopeController
@AllArgsConstructor
public class FindAllScopeController {

  private FindAllScopesPort findAllScopesPort;

  @ApiOperation(value = "Find scopes", notes = "Trae todas las scopes")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @GetMapping
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','READ')")
  public List<FindAllScopesOutputDto> findAll() {
    List<Scope> all = findAllScopesPort.findAll();
    return all.stream().map(FindAllScopesOutputDto::new).collect(Collectors.toList());
  }
}
