package com.spring.auth.client.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ClientController;
import com.spring.auth.client.infrastructure.repositories.ports.CreateClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.dtos.output.ClientOutputDto;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

@ClientController
@AllArgsConstructor
public class CreateClientController {

  private CreateClientPort createClientPort;

  @ApiOperation(value = "Create a client", notes = "Crea un nuevo client en la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @PostMapping
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','CREATE')")
  public ClientOutputDto create() throws DuplicatedKeyException {
    Client createdClient = createClientPort.create();
    return new ClientOutputDto(createdClient);
  }
}
