package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.LoginUserPort;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.session.application.ports.out.CreateSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.application.ports.out.FindUserPort;
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
      throws NotFoundException, WrongPasswordException, DuplicatedKeyException {

    // findAll the user trying to login
    User user = findUserPort.findByUsernameOrEmail(username, email);

    // check if its de correct password
    if (!user.doPasswordsMatch(password)) throw new WrongPasswordException("invalid password");

    // creating session
    Session session = new Session(user.getId());
    return createSessionPort.create(session);
  }
}
