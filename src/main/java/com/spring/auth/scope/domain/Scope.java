package com.spring.auth.scope.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Scope {

  private String id;
  private String name;
  private String description;
  private String value;

  public Scope(final String value) {
    this.value = value;
  }

  public Scope(final String name, final String description, final String value) {
    this.name = name;
    this.description = description;
    this.value = value;
  }
}
