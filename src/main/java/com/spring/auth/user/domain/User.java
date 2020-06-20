package com.spring.auth.user.domain;

import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.session.domain.Session;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ToString
public class User {

  private String id;
  private String username = "";
  private String email = "";
  private String password = "";
  private List<Role> roles = new ArrayList<>();
  private List<Scope> scopes = new ArrayList<>();
  private int maxSessions = 10;

  // not loaded by default, call LoadUserSessionsPort
  @Setter private List<Session> sessions;

  public User(
      final String username,
      final String email,
      final String password,
      final List<Role> roles,
      final List<Scope> scopes) {
    this.username = username;
    this.email = email;
    this.setPassword(password);
    this.roles = roles;
    this.scopes = scopes;
  }

  public User(
      String id,
      String username,
      String email,
      String password,
      List<Role> roles,
      List<Scope> scopes,
      Integer maxSessions) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.setPassword(password);
    this.roles = roles;
    this.scopes = scopes;
    this.maxSessions = maxSessions;
  }

  public User(final String id, final List<Role> roles, final List<Scope> scopes) {
    this.id = id;
    this.roles = roles;
    this.scopes = scopes;
  }

  public User(final String username, final String email, final String password) {
    this.username = username;
    this.email = email;
    this.setPassword(password);
  }

  public boolean doPasswordsMatch(final String password) {
    final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder.matches(password, this.password);
  }

  public void updatePassword(final String password) {
    if (StringUtils.isNotBlank(password)) {
      this.setPassword(password);
    }
  }

  private void setPassword(final String password) {
    this.password = password;
    this.hash();
  }

  private void hash() {
    String regex = "^\\$2[ayb]\\$.{56}$";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(this.getPassword());
    if (!m.matches()) {
      this.setPassword(hash(this.password));
    }
  }

  public boolean canHaveMoreSessions(int sessionCount) {
    return sessionCount < maxSessions;
  }

  // use lazy collection sessions, load before use
  public boolean canHaveMoreSessions() {
    return canHaveMoreSessions(this.getSessions().size());
  }

  private String hash(String p) {
    BCryptPasswordEncoder coder = new BCryptPasswordEncoder(6);
    return coder.encode(p);
  }

  public void updateRole(Role role) {
    if (!containsRole(role, this.roles)) return;
    removeRoles(Collections.singletonList(role.getId())); // to prevent duplicated
    addRoles(Collections.singletonList(role));
  }

  public void addRoles(List<Role> roles) {
    for (Role role : roles) {
      if (!containsRole(role, this.roles)) {
        this.roles.add(role);
      }
    }
    fixScopes();
  }

  public void removeRoles(List<String> roles) {
    for (String s : roles) {
      for (Role role : this.roles) {
        if (role.getId().equals(s)) {
          this.roles.remove(role);
          break;
        }
      }
    }
    fixScopes();
  }

  private void fixScopes() {
    final List<Scope> scopes = new ArrayList<>();
    for (Role role : this.roles) {
      for (Scope scope : role.getScopes()) {
        if (!containsScope(scope, scopes)) {
          scopes.add(scope);
        }
      }
    }
    this.scopes = scopes;
  }

  private boolean containsRole(Role role, List<Role> roles) {
    Optional<Role> optional =
        roles.stream().filter(s -> s.getId().equals(role.getId())).findFirst();
    return optional.isPresent();
  }

  private boolean containsScope(Scope scope, List<Scope> scopes) {
    Optional<Scope> optional =
        scopes.stream().filter(s -> s.getId().equals(scope.getId())).findFirst();
    return optional.isPresent();
  }

  public Role getRoleById(String id) {
    for (Role role : roles) if (role.getId().equals(id)) return role;
    return null;
  }
}
