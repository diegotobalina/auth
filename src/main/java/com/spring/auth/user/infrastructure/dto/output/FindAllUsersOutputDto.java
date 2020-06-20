package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FindAllUsersOutputDto {

  private String id; // id of the user
  private String username; // username of the user
  private String email; // email of the user
  private List<String> roles; // role ids of the user
  private List<String> scopes; // scope ids of the user
  private int maxSessions; // max sessions of the user

  public FindAllUsersOutputDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.roles = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
    this.scopes = user.getScopes().stream().map(Scope::getId).collect(Collectors.toList());
    this.maxSessions = user.getMaxSessions();
  }
}
