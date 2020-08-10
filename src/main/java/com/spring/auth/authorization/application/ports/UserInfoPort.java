package com.spring.auth.authorization.application.ports;

import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;

/** @author diegotobalina created on 24/06/2020 */
public interface UserInfoPort {
  User userInfo(Principal principal) throws NotFoundException, LockedUserException;

  User userInfo(String token, String clientId) throws NotFoundException, LockedUserException, InfiniteLoopException, GeneralSecurityException, InvalidTokenException, IOException, UnknownTokenFormatException, EmailDoesNotExistsException, GoogleGetInfoException, DuplicatedKeyException;
}
