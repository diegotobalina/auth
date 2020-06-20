package com.spring.auth.user.application.ports.out;

import com.spring.auth.user.domain.User;

import java.util.List;

/** Find all the users that have the scope using the scopeId */
public interface FindAllUsersByScopeIdPort {
  List<User> findAll(String scopeId);
}
