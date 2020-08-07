package com.spring.auth.google.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import com.spring.auth.user.application.ports.in.RegisterUserPort;
import com.spring.auth.user.application.ports.out.ExistsUserPort;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
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
  private UpdateUserPort updateUserPort;

  @Override
  public User login(final Payload payload)
      throws InfiniteLoopException, DuplicatedKeyException, NotFoundException, LockedUserException,
          EmailDoesNotExistsException {

    final String email = payload.getEmail();
    final Boolean emailVerified = payload.getEmailVerified();

    User user = getUserAfterLogin(email);
    loginUserWithGoogle(user);
    updateEmailVerified(user, emailVerified);
    return user;
  }

  /**
   * Return user after login process, user can already exists or will create a new one
   *
   * @param email Search if the user exists using this email
   * @return Found user or created one
   */
  private User getUserAfterLogin(String email)
      throws DuplicatedKeyException, NotFoundException, LockedUserException, InfiniteLoopException,
          EmailDoesNotExistsException {
    if (isUserInTheSystem(email)) return getUserIfNotLocked(email); // user already exists

    final String username = findUserPort.findAvailableUsername(email);
    final String randomPassword = UserUtil.generateRandomPassword();
    return registerUserPort.register(username, email, randomPassword);
  }

  private User getUserIfNotLocked(String email)
      throws NotFoundException, LockedUserException, DuplicatedKeyException {
    User user = findUserPort.findByEmail(email);
    if (user.isLocked()) throw new LockedUserException("this user is locked");
    loginUserWithGoogle(user);
    return user;
  }

  private void updateEmailVerified(User user, Boolean emailVerified) throws DuplicatedKeyException {
    if (user.isEmailVerified() == emailVerified) return; // doesnt need update
    user.verifyEmail(emailVerified);
    updateUserPort.update(user);
  }

  private void loginUserWithGoogle(User user) throws DuplicatedKeyException {
    if (user.isLoggedWithGoogle()) return; // doesnt need update
    user.loginGoogle();
    updateUserPort.update(user);
  }

  private boolean isUserInTheSystem(String email) {
    return existsUserPort.existsByEmail(email);
  }
}
