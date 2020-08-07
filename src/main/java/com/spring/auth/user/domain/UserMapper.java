package com.spring.auth.user.domain;

import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;

import java.util.Date;
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
    final Date createdAt = userJpa.getCreatedAt();
    final Date lastModified = userJpa.getLastModified();
    final String createdBy = userJpa.getCreatedBy();
    final String lastModifiedBy = userJpa.getLastModifiedBy();
    final String id = userJpa.getId();
    final String username = userJpa.getUsername();
    final String email = userJpa.getEmail();
    final String password = userJpa.getPassword();
    final List<Role> roles =
        userJpa.getRoles().stream().map(RoleMapper::parse).collect(Collectors.toList());
    final List<Scope> scopes =
        userJpa.getScopes().stream().map(ScopeMapper::parse).collect(Collectors.toList());
    final Integer maxSessions = userJpa.getMaxSessions();
    boolean locked = userJpa.isLocked();
    boolean loggedWithGoogle = userJpa.isLoggedWithGoogle();
    boolean emailVerified = userJpa.isEmailVerified();
    return new User(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        username,
        email,
        password,
        roles,
        scopes,
        maxSessions,
        locked,
        null,
        loggedWithGoogle,
        emailVerified);
  }

  public static UserJpa parse(User user) {
    final Date createdAt = user.getCreatedAt();
    final Date lastModified = user.getLastModified();
    final String createdBy = user.getCreatedBy();
    final String lastModifiedBy = user.getLastModifiedBy();
    final String id = user.getId();
    final String username = user.getUsername();
    final String email = user.getEmail();
    final String password = user.getPassword();
    final List<RoleJpa> rolesJpa =
        user.getRoles().stream().map(RoleMapper::parse).collect(Collectors.toList());
    final List<ScopeJpa> scopesJpa =
        user.getScopes().stream().map(ScopeMapper::parse).collect(Collectors.toList());
    final Integer maxSessions = user.getMaxSessions();
    boolean locked = user.isLocked();
    boolean loggedWithGoogle = user.isLoggedWithGoogle();
    boolean emailVerified = user.isEmailVerified();
    return new UserJpa(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        username,
        email,
        password,
        rolesJpa,
        scopesJpa,
        maxSessions,
        locked,
        loggedWithGoogle,
        emailVerified);
  }
}
