package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindAllRolesByIdPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.in.AddRolesToUserPort;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class AddRolesToUserUseCase implements AddRolesToUserPort {

  private UpdateUserPort updateUserPort;
  private FindUserByIdPort findUserByIdPort;
  private FindAllRolesByIdPort findAllRolesByIdPort;

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
    User user = findUserByIdPort.find(userId);
    List<Role> roles = findAllRolesByIdPort.findAll(roleIds);
    return add(user, roles);
  }
}
