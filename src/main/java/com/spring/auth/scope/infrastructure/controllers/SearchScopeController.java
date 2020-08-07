package com.spring.auth.scope.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.ScopeController;
import com.spring.auth.scope.application.ports.out.FindScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.dtos.output.SearchScopesOutputDto;
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

/** @author diegotobalina created on 24/06/2020 */
@ScopeController
@AllArgsConstructor
public class SearchScopeController {

  private FindScopePort findScopePort;

  @ApiOperation(
      value = "Search scopes",
      notes = "Trae todas las scopes que sigan los criterios de búsqueda")
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
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "value", required = false) String value,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Page<Scope> search = findScopePort.search(id, name, description, value, page, size);
    List<Scope> content = search.getContent();
    List<SearchScopesOutputDto> scopeInfoOutPutDtos =
        content.stream().map(SearchScopesOutputDto::new).collect(Collectors.toList());
    return new PagedListOutputDto(
        scopeInfoOutPutDtos, search.getTotalElements(), search.getTotalPages());
  }
}
