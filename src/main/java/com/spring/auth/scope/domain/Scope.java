package com.spring.auth.scope.domain;

import com.spring.auth.shared.domain.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Scope extends Auditable implements Serializable {

  private String id;
  private String name;
  private String description;
  private String value;

  public Scope(
      Date createdAt,
      Date lastModified,
      String createdBy,
      String lastModifiedBy,
      String id,
      String name,
      String description,
      String value) {
    super(createdAt, lastModified, createdBy, lastModifiedBy);
    this.id = id;
    this.name = name;
    this.description = description;
    this.value = value;
  }

  public Scope(final String value) {
    this.value = value;
  }

  public Scope(final String name, final String description, final String value) {
    this.name = name;
    this.description = description;
    this.value = value;
  }
}
