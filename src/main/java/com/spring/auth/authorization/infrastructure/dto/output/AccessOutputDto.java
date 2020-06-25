package com.spring.auth.authorization.infrastructure.dto.output;

import com.spring.auth.util.TokenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@Setter
@ToString
public class AccessOutputDto {

  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;

  public AccessOutputDto(TokenUtil.JwtWrapper jwtWrapper) {
    this.token = TokenUtil.addBearerPrefix(jwtWrapper.getToken());
    this.issuedAt = jwtWrapper.getIssuedAt();
    this.expiration = jwtWrapper.getExpiration();
    this.userId = jwtWrapper.getUserId();
  }
}
