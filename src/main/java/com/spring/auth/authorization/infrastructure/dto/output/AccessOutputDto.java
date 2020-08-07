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

  private String id_token;
  private Date issued_at;
  private Date expiration;
  private String user_id;

  public AccessOutputDto(TokenUtil.JwtWrapper jwtWrapper) {
    this.id_token = TokenUtil.addBearerPrefix(jwtWrapper.getToken());
    this.issued_at = jwtWrapper.getIssuedAt();
    this.expiration = jwtWrapper.getExpiration();
    this.user_id = jwtWrapper.getUserId();
  }
}
