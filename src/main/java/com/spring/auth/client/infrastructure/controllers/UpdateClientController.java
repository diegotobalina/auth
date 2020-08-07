package com.spring.auth.client.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ClientController;
import com.spring.auth.client.application.ports.in.UpdateClientValues;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.dtos.input.UpdateClientInputDto;
import com.spring.auth.client.infrastructure.dtos.output.ClientOutputDto;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@ClientController
@AllArgsConstructor
public class UpdateClientController {

  private UpdateClientValues updateClientValues;

  @ApiOperation(
      value = "Update Client values",
      notes = "Actualiza los valores de un Client")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @PutMapping("{clientId}")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','UPDATE')")
  public ClientOutputDto update(
      @PathVariable(value = "clientId") String clientId,
      @RequestBody @Valid UpdateClientInputDto updateClientInputDto)
      throws DuplicatedKeyException, NotFoundException {
    List<String> allowedUrls = updateClientInputDto.getAllowedUrls();
    List<String> allowedCallbackUrls = updateClientInputDto.getAllowedCallbackUrls();
    long expirationTokenTime = updateClientInputDto.getExpirationTokenTime();
    String googleClientId = updateClientInputDto.getGoogleClientId();
    Client client =
        updateClientValues.update(
            clientId, allowedUrls, allowedCallbackUrls, expirationTokenTime, googleClientId);
    return new ClientOutputDto(client);
  }
}
