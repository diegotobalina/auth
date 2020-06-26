package com.spring.auth.user.domain;

import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public abstract class UserMapper {

  public static List<User> parseUserJpaList(List<UserJpa> userJpas) {
    return userJpas.stream().map(UserMapper::parse).collect(Collectors.toList());
  }

  public static List<UserJpa> parseUserList(List<User> users) {
    return users.stream().map(UserMapper::parse).collect(Collectors.toList());
  }

  public static User parse(UserJpa userJpa) {
    final String id = userJpa.getId();
    final String username = userJpa.getUsername();
    final String email = userJpa.getEmail();
    final String password = userJpa.getPassword();
    final List<Role> roles =
        userJpa.getRoles().stream().map(RoleMapper::parse).collect(Collectors.toList());
    final List<Scope> scopes =
        userJpa.getScopes().stream().map(ScopeMapper::parse).collect(Collectors.toList());
    final Integer maxSessions = userJpa.getMaxSessions();
    return new User(id, username, email, password, roles, scopes, maxSessions);
  }

  public static UserJpa parse(User user) {
    final String id = user.getId();
    final String username = user.getUsername();
    final String email = user.getEmail();
    final String password = user.getPassword();
    final List<RoleJpa> rolesJpa =
        user.getRoles().stream().map(RoleMapper::parse).collect(Collectors.toList());
    final List<ScopeJpa> scopesJpa =
        user.getScopes().stream().map(ScopeMapper::parse).collect(Collectors.toList());
    final int maxSessions = user.getMaxSessions();
    return new UserJpa(id, username, email, password, rolesJpa, scopesJpa, maxSessions);
  }
}
