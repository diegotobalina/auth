package com.spring.auth.authorization.infrastructure.dto.output;

import com.spring.auth.client.domain.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 31/07/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthorizeOutputDto {
  private String id_token;

  public AuthorizeOutputDto(String token) {
    this.id_token = token;
  }
}

