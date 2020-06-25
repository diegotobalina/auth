package com.spring.auth.session.infrastructure.repositories.jpa;

import com.spring.auth.session.domain.SessionJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
public interface SessionRepositoryJpa extends MongoRepository<SessionJpa, String> {
  Optional<SessionJpa> findByToken(String value);

  List<SessionJpa> findAllByUserId(String userId);

  int countAllByUserId(String userId);

  Optional<SessionJpa> findFirstByUserIdOrderByExpiration(String userId);

  List<SessionJpa> findAllByExpirationBefore(Date expirationDate);

  boolean existsByToken(String token);
}
