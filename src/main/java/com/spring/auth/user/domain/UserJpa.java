package com.spring.auth.user.domain;

import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.scope.domain.ScopeJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spring_user")
public class UserJpa {

  @Id @Indexed private String id;

  @Indexed private String username;

  @Indexed private String email;

  private String password;
  @Indexed private List<RoleJpa> roles;
  @Indexed private List<ScopeJpa> scopes;
  private int maxSessions = 10;
}
