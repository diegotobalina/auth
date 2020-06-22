package com.spring.auth.authorization.infrastructure.dto.output;

import com.spring.auth.authorization.domain.JwtWrapper;
import com.spring.auth.util.TokenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AccessOutputDto {

  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;

  public AccessOutputDto(JwtWrapper jwtWrapper) {
    this.token = TokenUtil.addBearerPrefix(jwtWrapper.getToken());
    this.issuedAt = jwtWrapper.getIssuedAt();
    this.expiration = jwtWrapper.getExpiration();
    this.userId = jwtWrapper.getUserId();
  }
}
