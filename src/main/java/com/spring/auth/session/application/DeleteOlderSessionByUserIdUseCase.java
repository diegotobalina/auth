package com.spring.auth.session.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.session.application.ports.in.DeleteOlderSessionByUserIdPort;
import com.spring.auth.session.domain.SessionJpa;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class DeleteOlderSessionByUserIdUseCase implements DeleteOlderSessionByUserIdPort {

  private MongoTemplate mongoTemplate;

  @Async
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(final String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    query.with(Sort.by(Sort.Direction.ASC, "expiration"));
    query.limit(1);
    mongoTemplate.remove(query, SessionJpa.class);
  }
}
