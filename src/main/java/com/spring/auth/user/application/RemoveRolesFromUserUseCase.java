package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.RemoveRolesFromUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.UpdateUserPort;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class RemoveRolesFromUserUseCase implements RemoveRolesFromUserPort {

  private FindUserPort findUserPort;
  private UpdateUserPort updateUserPort;

  /**
   * Remove the roles from the user and save it in the database
   *
   * @param user User that need the role removals
   * @param roleIds RoleId of the roles that will be removed
   * @return Updates user
   * @throws DuplicatedKeyException If there is a problem saving the user for things like duplicated
   *     username
   */
  @Override
  public User remove(User user, List<String> roleIds) throws DuplicatedKeyException {
    user.removeRoles(roleIds);
    return updateUserPort.update(user);
  }

  /**
   * Remove the roles from the user and save it in the database
   *
   * @param userId Used to find the user in the database
   * @param roleIds RoleId of the roles that will be removed
   * @return Updates user
   * @throws NotFoundException If the user was not found with the id
   * @throws DuplicatedKeyException If there is a problem saving the user for things like duplicated
   *     username
   */
  @Override
  public User remove(String userId, List<String> roleIds)
      throws NotFoundException, DuplicatedKeyException {
    User user = findUserPort.findById(userId);
    return remove(user, roleIds);
  }

  @Override
  public List<User> remove(List<String> roleIds) throws DuplicatedKeyException {
    List<User> users = findUserPort.findAllByRoleIds(roleIds);
    this.remove(users, roleIds);
    return users;
  }

  @Override
  public List<User> remove(String roleId) throws DuplicatedKeyException {
    return remove(List.of(roleId));
  }

  @Override
  public List<User> remove(List<User> User, List<String> roleIds) throws DuplicatedKeyException {
    List<User> users = findUserPort.findAllByRoleIds(roleIds);
    for (User user : users) user.removeRoles(roleIds);
    return updateUserPort.updateAll(users);
  }
}
