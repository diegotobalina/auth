package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.in.RemoveRolesFromUserPort;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.List;

@UseCase
@AllArgsConstructor
public class RemoveRolesFromUserUseCase implements RemoveRolesFromUserPort {

  private FindUserByIdPort findUserByIdPort;
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
    User user = findUserByIdPort.find(userId);
    return remove(user, roleIds);
  }
}
