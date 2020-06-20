package com.spring.auth.scope.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spring_scope")
public class ScopeJpa {

  @Id @Indexed private String id;
  private String name;
  private String description;

  @Indexed private String value;
}
