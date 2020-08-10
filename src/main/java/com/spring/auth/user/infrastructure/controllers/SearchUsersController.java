package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.shared.infrastructure.dto.output.PagedListOutputDto;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.output.SearchUsersOutputDto;
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
@UserController
@AllArgsConstructor
public class SearchUsersController {

  private FindUserPort findUserPort;

  @ApiOperation(
      value = "Search users",
      notes = "Trae todos los usuarios que sigan los criterios de búsqueda")
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
      @RequestParam(value = "username", required = false) String username,
      @RequestParam(value = "email", required = false) String email,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size) {
    Page<User> search = findUserPort.search(id, username, email, page, size);
    List<User> content = search.getContent();
    List<SearchUsersOutputDto> userInfoOutPutDtos =
        content.stream().map(SearchUsersOutputDto::new).collect(Collectors.toList());
    return new PagedListOutputDto(
        userInfoOutPutDtos, search.getTotalElements(), search.getTotalPages());
  }
}
