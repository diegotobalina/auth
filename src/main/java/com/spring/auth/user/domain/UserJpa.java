package com.spring.auth.user.domain;

import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.shared.domain.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@ToString
@NoArgsConstructor
@Document(collection = "spring_user")
public class UserJpa extends Auditable {

  @Setter @Id @Indexed private String id;

  @Setter @Indexed private String username;

  @Setter @Indexed private String email;

  private String password;
  @Setter @Indexed private List<RoleJpa> roles;
  @Setter @Indexed private List<ScopeJpa> scopes;
  private int maxSessions = 10;
  private boolean locked = false;

  private boolean loggedWithGoogle = false;
  private boolean emailVerified = false;

  public UserJpa(
      Date createdAt,
      Date lastModified,
      String createdBy,
      String lastModifiedBy,
      String id,
      String username,
      String email,
      String password,
      List<RoleJpa> roles,
      List<ScopeJpa> scopes,
      int maxSessions,
      boolean locked,
      boolean loggedWithGoogle,
      boolean emailVerified) {
    super(createdAt, lastModified, createdBy, lastModifiedBy);
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.scopes = scopes;
    this.maxSessions = maxSessions;
    this.locked = locked;
    this.loggedWithGoogle = loggedWithGoogle;
    this.emailVerified = emailVerified;
  }
}
