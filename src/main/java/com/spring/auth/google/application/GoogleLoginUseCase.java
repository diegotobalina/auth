package com.spring.auth.google.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.google.application.ports.GoogleLoginPort;
import com.spring.auth.google.infrastructure.repositories.ports.GoogleGetInfoPort;
import com.spring.auth.user.application.ports.ThirdPartyLoginPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@UseCase
@AllArgsConstructor
public class GoogleLoginUseCase implements GoogleLoginPort {

  private ThirdPartyLoginPort thirdPartyLoginPort;
  private GoogleGetInfoPort googleGetInfoPort;

  @Override
  public User login(String jwt, String googleClientId)
      throws NotFoundException, LockedUserException, EmailDoesNotExistsException,
          DuplicatedKeyException, InfiniteLoopException, GeneralSecurityException, IOException,
          GoogleGetInfoException {
    Payload payload = googleGetInfoPort.get(jwt, googleClientId);
    return login(payload);
  }

  @Override
  public User login(String jwt)
      throws NotFoundException, LockedUserException, EmailDoesNotExistsException,
          DuplicatedKeyException, InfiniteLoopException, GeneralSecurityException, IOException,
          GoogleGetInfoException {
    Payload payload = googleGetInfoPort.get(jwt);
    return login(payload);
  }

  @Override
  public User login(final Payload payload)
      throws InfiniteLoopException, DuplicatedKeyException, NotFoundException, LockedUserException,
          EmailDoesNotExistsException {

    final String email = payload.getEmail();
    final Boolean emailVerified = payload.getEmailVerified();
    return thirdPartyLoginPort.thirdPartyLogin(email, emailVerified);
  }
}
