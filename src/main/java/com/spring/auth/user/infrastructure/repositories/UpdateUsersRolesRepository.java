package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.application.ports.out.UpdateUsersRolesPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UpdateUsersRolesRepository implements UpdateUsersRolesPort {

  private UpdateUsersPort updateUsersPort;

  /**
   * Update users roles and save them in the database
   *
   * @param users Users that must be updated
   * @param roles Roles that must be updated in the users
   * @return List with the updated users
   * @throws DuplicatedKeyException When some user have problems with the costraints, for example
   *     duplicated username
   */
  @Override
  public List<User> update(List<User> users, List<Role> roles) throws DuplicatedKeyException {
    for (User user : users) roles.forEach(user::updateRole);
    return updateUsersPort.updateAll(users);
  }
}
