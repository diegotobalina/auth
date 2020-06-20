package com.spring.auth.role.domain;

import com.spring.auth.scope.domain.ScopeJpa;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spring_role")
public class RoleJpa {

  @Id @Indexed private String id;
  @Setter private String name;
  private String description;
  @Indexed private String value;
  @Indexed private List<ScopeJpa> scopes;
}
