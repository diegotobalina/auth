package com.spring.auth.authorization.infrastructure.dto.output;

import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
public class UserInfoOutPutDto {
  private String id;
  private String username;
  private String email;
  private List<String> roles;
  private List<String> scopes;
  private boolean loggedWithGoogle;
  private boolean emailVerified;

  public UserInfoOutPutDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.roles = user.getRoles().stream().map(Role::getValue).collect(Collectors.toList());
    this.scopes = user.getScopes().stream().map(Scope::getValue).collect(Collectors.toList());
    this.loggedWithGoogle = user.isLoggedWithGoogle();
    this.emailVerified = user.isEmailVerified();
  }
}
