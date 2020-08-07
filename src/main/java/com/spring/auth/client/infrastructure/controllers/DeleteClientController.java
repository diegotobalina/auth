package com.spring.auth.client.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ClientController;
import com.spring.auth.client.application.ports.out.DeleteClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.dtos.output.ClientOutputDto;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ClientController
@AllArgsConstructor
public class DeleteClientController {

  private DeleteClientPort deleteClientPort;

  @ApiOperation(value = "Delete a client", notes = "Borra un client de la base de datos")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @DeleteMapping("{clientId}")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','DELETE')")
  public ClientOutputDto delete(@PathVariable(value = "clientId") String clientId)
      throws DuplicatedKeyException, NotFoundException {
    Client deletedClient = deleteClientPort.delete(clientId);
    return new ClientOutputDto(deletedClient);
  }
}
