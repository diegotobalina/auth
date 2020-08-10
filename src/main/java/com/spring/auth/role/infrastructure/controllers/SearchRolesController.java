package com.spring.auth.role.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.RoleController;
import com.spring.auth.role.infrastructure.repositories.ports.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.dtos.output.SearchRolesOutputDto;
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
@RoleController
@AllArgsConstructor
public class SearchRolesController {

  private FindRolePort findRolePort;

  @ApiOperation(
      value = "Search roles",
      notes = "Trae todas los roles que sigan los criterios de búsqueda")
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
    Page<Role> search = findRolePort.search(id, name, description, value, page, size);
    List<Role> content = search.getContent();
    List<SearchRolesOutputDto> roleInfoOutPutDtos =
        content.stream().map(SearchRolesOutputDto::new).collect(Collectors.toList());
    return new PagedListOutputDto(
        roleInfoOutPutDtos, search.getTotalElements(), search.getTotalPages());
  }
}
