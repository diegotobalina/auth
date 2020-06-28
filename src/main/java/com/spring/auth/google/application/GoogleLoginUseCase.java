package com.spring.auth.google.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import com.spring.auth.user.application.ports.in.RegisterUserPort;
import com.spring.auth.user.application.ports.out.ExistsUserPort;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@UseCase
@AllArgsConstructor
public class GoogleLoginUseCase implements GoogleLoginPort {

  private FindUserPort findUserPort;
  private ExistsUserPort existsUserPort;
  private RegisterUserPort registerUserPort;

  @Override
  public User login(final Payload payload)
      throws InfiniteLoopException, DuplicatedKeyException, NotFoundException, LockedUserException,
          EmailDoesNotExistsException {
    final String email = payload.getEmail();
    // if the user exists in the system should not be registered again
    if (isUserInTheSystem(email)) return getUserIfNotLocked(email);
    // user not in the system, register the user
    final String username = findUserPort.findAvailableUsername(email);
    final String randomPassword = UserUtil.generateRandomPassword();
    return registerUserPort.register(username, email, randomPassword);
  }

  private User getUserIfNotLocked(String email) throws NotFoundException, LockedUserException {
    User user = findUserPort.findByEmail(email);
    if (user.isLocked()) throw new LockedUserException("this user is locked");
    return user;
  }

  private boolean isUserInTheSystem(String email) {
    return existsUserPort.existsByEmail(email);
  }
}
