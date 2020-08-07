package com.spring.auth.session.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public abstract class SessionMapper {

  public static List<Session> parseSessionJpaList(List<SessionJpa> sessionJpaList) {
    return sessionJpaList.stream().map(SessionMapper::parse).collect(Collectors.toList());
  }

  public static List<SessionJpa> parseSessionList(List<Session> sessionList) {
    return sessionList.stream().map(SessionMapper::parse).collect(Collectors.toList());
  }

  public static Session parse(final SessionJpa sessionJpa) {
    final Date createdAt = sessionJpa.getCreatedAt();
    final Date lastModified = sessionJpa.getLastModified();
    final String createdBy = sessionJpa.getCreatedBy();
    final String lastModifiedBy = sessionJpa.getLastModifiedBy();
    final String id = sessionJpa.getId();
    final String token = sessionJpa.getToken();
    final Date issuedAt = sessionJpa.getIssuedAt();
    final Date expiration = sessionJpa.getExpiration();
    final String userId = sessionJpa.getUserId();
    return new Session(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        token,
        issuedAt,
        expiration,
        userId);
  }

  public static SessionJpa parse(final Session session) {
    final Date createdAt = session.getCreatedAt();
    final Date lastModified = session.getLastModified();
    final String createdBy = session.getCreatedBy();
    final String lastModifiedBy = session.getLastModifiedBy();
    final String id = session.getId();
    final String token = session.getToken();
    final Date issuedAt = session.getIssuedAt();
    final Date expiration = session.getExpiration();
    final String userId = session.getUserId();
    return new SessionJpa(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        token,
        issuedAt,
        expiration,
        userId);
  }
}
