package com.spring.auth.authorization.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class JwtWrapper {
  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;
}
