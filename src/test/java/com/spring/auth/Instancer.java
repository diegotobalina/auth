package com.spring.auth;

import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordAdminInputDto;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordInputDto;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public class Instancer {

  ObjectFiller objectFiller = new ObjectFiller();

  @SneakyThrows
  public User user() {
    User user = new User();
    objectFiller.replace(user, "id", "id");
    objectFiller.replace(user, "username", "username");
    objectFiller.replace(user, "email", "email@user.com");
    objectFiller.replace(user, "password", "password");
    objectFiller.replace(user, "roles", new ArrayList<>());
    objectFiller.replace(user, "scopes", new ArrayList<>());
    objectFiller.replace(user, "sessions", null);
    objectFiller.replace(user, "maxSessions", 10);
    return user;
  }

  @SneakyThrows
  public Role role() {
    Role role = new Role();
    objectFiller.replace(role, "id", "id");
    objectFiller.replace(role, "name", "name");
    objectFiller.replace(role, "description", "description");
    objectFiller.replace(role, "value", "value");
    objectFiller.replace(role, "scopes", new ArrayList<>());
    return role;
  }

  @SneakyThrows
  public Scope scope() {
    Scope scope = new Scope();
    objectFiller.replace(scope, "id", "scope");
    objectFiller.replace(scope, "name", "name");
    objectFiller.replace(scope, "description", "description");
    objectFiller.replace(scope, "value", "value");
    return scope;
  }

  @SneakyThrows
  public Session session() {
    Session session = new Session();
    objectFiller.replace(session, "id", "id");
    objectFiller.replace(session, "token", "token");
    objectFiller.replace(session, "issuedAt", new Date());
    objectFiller.replace(session, "expiration", new Date());
    objectFiller.replace(session, "userId", "userId");
    return session;
  }

  @SneakyThrows
  public SimpleGrantedAuthority simpleGrantedAuthority() {
    return new SimpleGrantedAuthority("credential");
  }

  @SneakyThrows
  public UpdatePasswordInputDto updatePasswordInputDto() {
    UpdatePasswordInputDto updatePasswordInputDto = new UpdatePasswordInputDto();
    objectFiller.replace(updatePasswordInputDto, "oldPassword", "oldPassword");
    objectFiller.replace(updatePasswordInputDto, "newPassword", "newPassword");
    return updatePasswordInputDto;
  }

  @SneakyThrows
  public Principal principal(User user) {
    SimpleGrantedAuthority credential = simpleGrantedAuthority();
    List<SimpleGrantedAuthority> credentials = Arrays.asList(credential, credential);
    return new UsernamePasswordAuthenticationToken(user, credentials);
  }

  @SneakyThrows
  public UpdatePasswordAdminInputDto updatePasswordAdminInputDto() {
    UpdatePasswordAdminInputDto updatePasswordAdminInputDto = new UpdatePasswordAdminInputDto();
    objectFiller.replace(updatePasswordAdminInputDto, "newPassword", "newPassword");
    return updatePasswordAdminInputDto;
  }
}
