package com.spring.auth.session.application.ports.out;

import com.spring.auth.session.domain.Session;

import java.util.List;

public interface FindAllExpiredSessionsPort {
  List<Session> findAll();
}
