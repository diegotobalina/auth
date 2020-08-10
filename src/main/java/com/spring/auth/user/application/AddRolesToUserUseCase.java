package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.infrastructure.repositories.ports.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.AddRolesToUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class AddRolesToUserUseCase implements AddRolesToUserPort {

  private UpdateUserPort updateUserPort;
  private FindUserPort findUserPort;
  private FindRolePort findRolePort;

  /**
   * Add roles to a user
   *
   * @param user The user that need the roles
   * @param roles Roles that will be added to the user
   * @return Updated user
   * @throws DuplicatedKeyException If there is a problem saving the user, like duplicated username
   */
  @Override
  public User add(User user, List<Role> roles) throws DuplicatedKeyException {
    user.addRoles(roles);
    return updateUserPort.update(user);
  }

  @Override
  public User add(String userId, List<String> roleIds)
      throws NotFoundException, DuplicatedKeyException {
    User user = findUserPort.findById(userId);
    List<Role> roles = findRolePort.findAllByIds(roleIds);
    return add(user, roles);
  }
}
