package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateUserRepository implements UpdateUserPort {

  private UpdateUsersPort updateUsersPort;

  /**
   * Save the existing user in the database with the new data. Before save the user check for
   * duplicated username o duplicated email.
   *
   * @param user User that must be updated
   * @return List with the updated users
   * @throws DuplicatedKeyException When some constraint fails, for example duplicated username
   */
  @Override
  public User update(User user) throws DuplicatedKeyException {
    List<User> updatedUsers = updateUsersPort.updateAll(Collections.singletonList(user));
    return updatedUsers.stream().findFirst().get();
  }
}
