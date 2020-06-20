package com.spring.auth.session.application.ports.out;

public interface CountAllSessionsByUserIdPort {
  int countAll(String userId);
}
