package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.in.RemoveRolesFromUsersPort;
import com.spring.auth.user.application.ports.out.FindAllUsersByRoleIdsPort;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class RemoveRolesFromUsersUseCase implements RemoveRolesFromUsersPort {

  private FindAllUsersByRoleIdsPort findAllUsersByRoleIdsPort;
  private UpdateUsersPort updateUsersPort;

  /**
   * Remove the roles from ALL the users and save them in the database
   *
   * @param roleIds If of the roles that will be removed
   * @return Updated users
   * @throws DuplicatedKeyException If there was a problem saving the users
   */
  @Override
  public List<User> remove(List<String> roleIds) throws DuplicatedKeyException {
    List<User> users = findAllUsersByRoleIdsPort.findAll(roleIds);
    users.forEach(user -> user.removeRoles(roleIds));
    return updateUsersPort.updateAll(users);
  }
}
