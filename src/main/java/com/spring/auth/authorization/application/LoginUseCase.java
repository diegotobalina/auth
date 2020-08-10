package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.LoginUserPort;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.session.infrastructure.repositories.ports.CreateSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
  public class LoginUseCase implements LoginUserPort {

  private FindUserPort findUserPort;
  private CreateSessionPort createSessionPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Session login(String username, String email, String password)
          throws NotFoundException, DuplicatedKeyException, LockedUserException, WrongPasswordException {

    User user = findUserPort.findByUsernameOrEmail(username, email);
    if (!user.doPasswordsMatch(password)) throw new WrongPasswordException("invalid password");

    // check if the user is locked
    if (user.isLocked()) throw new LockedUserException("this user is locked");

    // creating session
    Session session = new Session(user.getId());
    return createSessionPort.create(session);
  }
}
