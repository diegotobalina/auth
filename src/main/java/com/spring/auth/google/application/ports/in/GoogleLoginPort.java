package com.spring.auth.google.application.ports.in;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
public interface GoogleLoginPort {
  User login(GoogleIdToken.Payload payload)
          throws InfiniteLoopException, DuplicatedKeyException, NotFoundException, LockedUserException, EmailDoesNotExistsException;
}
