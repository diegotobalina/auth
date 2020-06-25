package com.spring.auth.user.application.ports.out;

import com.spring.auth.user.domain.User;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
/** Find all users in the database */
public interface FindAllUsersPort {
  List<User> findAll();
}
