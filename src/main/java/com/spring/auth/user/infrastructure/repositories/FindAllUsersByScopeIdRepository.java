package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.FindAllUsersByScopeIdPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllUsersByScopeIdRepository implements FindAllUsersByScopeIdPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Find users using a scope id ( user has an scope list )
   *
   * @param scopeId Id of the scope that the user must have
   * @return List with all the users
   */
  @Override
  public List<User> findAll(final String scopeId) {
    List<UserJpa> userJpas = this.userRepositoryJpa.findAllByScopesId(scopeId);
    return userJpas.stream().map(UserMapper::parse).collect(Collectors.toList());
  }
}
