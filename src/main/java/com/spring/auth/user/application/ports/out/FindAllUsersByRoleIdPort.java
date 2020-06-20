package com.spring.auth.user.application.ports.out;

import com.spring.auth.user.domain.User;

import java.util.List;

/** Find all the users that have the role using the roleId */
public interface FindAllUsersByRoleIdPort {
  List<User> findAll(String roleId);
}
