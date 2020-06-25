package com.spring.auth.session.application.ports.out;

import com.spring.auth.session.domain.Session;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindAllExpiredSessionsPort {
  List<Session> findAll();
}
