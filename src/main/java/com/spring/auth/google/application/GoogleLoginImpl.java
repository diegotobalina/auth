package com.spring.auth.google.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.InfiniteLoopException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.google.application.ports.out.GoogleLoginPort;
import com.spring.auth.shared.util.UserUtil;
import com.spring.auth.user.application.ports.in.RegisterUserPort;
import com.spring.auth.user.application.ports.out.ExistsUserByEmailPort;
import com.spring.auth.user.application.ports.out.ExistsUserByUserNamePort;
import com.spring.auth.user.application.ports.out.FindUserByEmailPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@AllArgsConstructor
public class GoogleLoginImpl implements GoogleLoginPort {

  private FindUserByEmailPort findUserByEmailPort;
  private ExistsUserByUserNamePort existsUserByUserNamePort;
  private ExistsUserByEmailPort existsUserByEmailPort;
  private RegisterUserPort registerUserPort;

  @Override
  public User login(final Payload payload)
      throws InfiniteLoopException, DuplicatedKeyException, NotFoundException {
    final String email = payload.getEmail();
    // if the user exists in the system should not be registered again
    if (existsUserByEmailPort.exists(email)) return findUserByEmailPort.find(email);
    // user not in the system, register the user
    final String username = this.findAvailableUsername(email);
    final String randomPassword = UserUtil.generateRandomPassword();
    return registerUserPort.register(username, email, randomPassword);
  }

  private String findAvailableUsername(final String email) throws InfiniteLoopException {
    for (int i = 0; i < 10; i++) { // loop for limited attempts
      final String username = UserUtil.generateUsername(email);
      if (isAvailable(username)) return username;
    }
    throw new InfiniteLoopException("can not find an empty username");
  }

  private boolean isAvailable(final String username) {
    return !existsUserByUserNamePort.exists(username);
  }
}
