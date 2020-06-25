package com.spring.auth.session.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/** @author diegotobalina created on 24/06/2020 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Document(collection = "spring_session")
public class SessionJpa {

  @Id private String id;

  @Indexed private String token;

  private Date issuedAt;
  @Indexed private Date expiration;
  @Indexed private String userId;
}
