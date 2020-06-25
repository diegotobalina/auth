package com.spring.auth.authorization.infrastructure.dto.output;

import com.spring.auth.session.domain.Session;
import com.spring.auth.util.TokenUtil;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/** @author diegotobalina created on 24/06/2020 */
@Getter
@ToString
public class LoginOutputDto {

  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;

  public LoginOutputDto(Session session) {
    this.token = TokenUtil.addBearerPrefix(session.getToken());
    this.issuedAt = session.getIssuedAt();
    this.expiration = session.getExpiration();
    this.userId = session.getUserId();
  }
}
