package com.spring.auth.authorization.infrastructure.dto.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.spring.auth.authorization.domain.TokenInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class TokenInfoOutputDto {

  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;
  private List<String> roles;
  private List<String> scopes;

  public TokenInfoOutputDto(TokenInfo tokenInfo) {
    this.token = tokenInfo.getToken();
    this.issuedAt = tokenInfo.getIssuedAt();
    this.expiration = tokenInfo.getExpiration();
    this.userId = tokenInfo.getUserId();
    this.roles = tokenInfo.getRoles();
    this.scopes = tokenInfo.getScopes();
  }
}
