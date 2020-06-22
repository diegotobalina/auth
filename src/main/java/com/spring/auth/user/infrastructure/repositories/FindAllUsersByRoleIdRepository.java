package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.FindAllUsersByRoleIdPort;
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
public class FindAllUsersByRoleIdRepository implements FindAllUsersByRoleIdPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Find all the users that have the role by the roleId
   *
   * @param roleId RoleId that users must have
   * @return List with all the found users
   */
  @Override
  public List<User> findAll(final String roleId) {
    List<UserJpa> userJpas = this.userRepositoryJpa.findAllByRolesId(roleId);
    return userJpas.stream().map(UserMapper::parse).collect(Collectors.toList());
  }
}
