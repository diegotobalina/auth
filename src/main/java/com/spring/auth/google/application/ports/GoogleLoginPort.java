package com.spring.auth.google.application.ports;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
public interface GoogleLoginPort {
  User login(GoogleIdToken.Payload payload)
          throws InfiniteLoopException, DuplicatedKeyException, NotFoundException, LockedUserException, EmailDoesNotExistsException;
  User login(String jwt,String googleClientId) throws NotFoundException, LockedUserException, EmailDoesNotExistsException, DuplicatedKeyException, InfiniteLoopException, GeneralSecurityException, IOException, GoogleGetInfoException;
  User login(String jwt) throws NotFoundException, LockedUserException, EmailDoesNotExistsException, DuplicatedKeyException, InfiniteLoopException, GeneralSecurityException, IOException, GoogleGetInfoException;
}
