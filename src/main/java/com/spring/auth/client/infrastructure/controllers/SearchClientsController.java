package com.spring.auth.client.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ClientController;
import com.spring.auth.client.infrastructure.repositories.ports.FindClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.dtos.output.ClientOutputDto;
import com.spring.auth.shared.infrastructure.dto.output.PagedListOutputDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@ClientController
@AllArgsConstructor
public class SearchClientsController {

  private FindClientPort findClientPort;

  @ApiOperation(
      value = "Search clients",
      notes = "Trae todas los clients que sigan los criterios de búsqueda")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @GetMapping("search")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','READ')")
  public PagedListOutputDto findAll(
      @RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "clientId", required = false) String clientId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Page<Client> search = findClientPort.search(id, clientId, page, size);
    List<Client> content = search.getContent();
    List<ClientOutputDto> clientInfoOutPutDtos =
        content.stream().map(ClientOutputDto::new).collect(Collectors.toList());
    return new PagedListOutputDto(
        clientInfoOutPutDtos, search.getTotalElements(), search.getTotalPages());
  }
}
