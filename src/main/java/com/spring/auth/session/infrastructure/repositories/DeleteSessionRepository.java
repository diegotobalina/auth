package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import com.spring.auth.session.infrastructure.repositories.ports.DeleteSessionPort;
import com.spring.auth.session.infrastructure.repositories.ports.FindSessionPort;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class DeleteSessionRepository implements DeleteSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;
  private FindSessionPort findSessionPort;
  private MongoTemplate mongoTemplate;

  @Override
  public void deleteByUserId(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    mongoTemplate.remove(query, SessionJpa.class);
  }

  @Override
  public void delete(final Session session) {
    this.sessionRepositoryJpa.deleteById(session.getId());
  }

  @Override
  public void delete(String token) throws NotFoundException {
    Session session = findSessionPort.findByToken(token);
    this.delete(session);
  }
}
