package com.spring.auth.scope.domain;

import com.spring.auth.shared.domain.Auditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spring_scope")
public class ScopeJpa extends Auditable {

  @Setter @Id @Indexed private String id;
  @Setter private String name;
  @Setter private String description;

  @Setter @Indexed private String value;

  public ScopeJpa(
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
}
