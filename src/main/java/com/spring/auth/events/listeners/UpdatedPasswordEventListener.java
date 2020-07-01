package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.UpdatedPasswordEvent;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class UpdatedPasswordEventListener {

  private MongoTemplate mongoTemplate;

  /** When user change the password all sessions should be removed */
  @Async
  @TransactionalEventListener
  public void removeUserSessions(UpdatedPasswordEvent updatedPasswordEvent) {
    User user = updatedPasswordEvent.getSource();
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(user.getId()));
    mongoTemplate.remove(query, SessionJpa.class);
  }
}
