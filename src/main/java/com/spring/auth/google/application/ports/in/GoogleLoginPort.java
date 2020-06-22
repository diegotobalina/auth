package com.spring.auth.google.application.ports.in;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.InfiniteLoopException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

public interface GoogleLoginPort {
  User login(GoogleIdToken.Payload payload)
      throws InfiniteLoopException, DuplicatedKeyException, NotFoundException;
}
