package com.spring.auth.session.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * Token entity
 *
 * @author diegotobalina
 */
@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class Session implements Comparable<Session> {

  private String id;
  private String token;
  private Date issuedAt;
  private Date expiration;
  private String userId;

  public Session(final String userId) {
    this.token = generateToken();
    this.issuedAt = new Date();
    this.expiration = generateExpiration();
    this.userId = userId;
  }

  public Boolean isValid() {
    return this.getExpiration().getTime() >= System.currentTimeMillis();
  }

  public void refresh() {
    this.expiration = this.generateExpiration();
  }

  private String generateToken() {
    return System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
  }

  private Date generateExpiration() {
    return new Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000));
  }

  @Override
  public int compareTo(final Session o) {
    return getExpiration().compareTo(o.getExpiration());
  }
}
