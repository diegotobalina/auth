package com.spring.auth.session.application.ports.out;

import com.spring.auth.session.domain.Session;

import java.util.List;

public interface FindAllSessionsByUserIdPort {
  List<Session> findAll(String userId);
}
