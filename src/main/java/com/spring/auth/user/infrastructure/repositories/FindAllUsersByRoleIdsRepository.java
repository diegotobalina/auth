package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.FindAllUsersByRoleIdsPort;
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
public class FindAllUsersByRoleIdsRepository implements FindAllUsersByRoleIdsPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Find all the users that have any of the roles
   *
   * @param roleIds List with the roleId that users must have
   * @return List with all the found users
   */
  @Override
  public List<User> findAll(List<String> roleIds) {
    List<UserJpa> allByRolesIdIn = this.userRepositoryJpa.findAllByRolesIdIn(roleIds);
    return allByRolesIdIn.stream().map(UserMapper::parse).collect(Collectors.toList());
  }
}
