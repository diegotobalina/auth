package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.UnlockUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class UnlockUserUseCase implements UnlockUserPort {

  private UpdateUserPort updateUserPort;
  private FindUserPort findUserPort;

  @Override
  public User unlock(String userId) throws DuplicatedKeyException, NotFoundException {
    User user = findUserPort.findById(userId);
    return unlock(user);
  }

  @Override
  public User unlock(User user) throws DuplicatedKeyException {
    user.unlock();
    return updateUserPort.update(user);
  }
}
