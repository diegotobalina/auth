package com.spring.auth.session.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;

import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindSessionPort {
  List<Session> findAll();

  List<Session> findAllByUserId(String userId);

  List<Session> findAllByExpirationBefore(Date date);

  Session findByToken(String token) throws NotFoundException;

  Session findOlderByUserId(String userId) throws NotFoundException;
}
